package com.felarca.ootp.domain;

import java.util.HashMap;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.springframework.beans.factory.annotation.Autowired;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.Repositories.Stats72Repository;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class OotpModel {

    @Autowired
    Stats72Repository stats72Repo;

    @Autowired
    CardsRepository cardsRepo;

	@Setter
	@Getter
	private String displayName;

	@Setter
	@Getter
	private String dbName;

	@Setter
	@Getter
	private Era defaultEra;

	@Setter
	@Getter
	private String urlSegment;

	@Getter
	@Setter
	private double fipConstant = 3.2;

	@Getter
	@Setter
	private PolynomialFunction walkFunction;

	@Getter
	@Setter
	private PolynomialFunction homerunFunction;

	@Getter
	@Setter
	private PolynomialFunction babipFunction;

	@Getter
	@Setter
	private PolynomialFunction kFunction;

	@Getter
	@Setter
	private PolynomialFunction doubleFunction;

	@Getter
	@Setter
	private PolynomialFunction pWalkFunction;

	@Getter
	@Setter
	private PolynomialFunction pHomerunFunction;

	@Getter
	@Setter
	private PolynomialFunction pBabipFunction;

	@Getter
	@Setter
	private PolynomialFunction pKFunction;

	@Getter
	@Setter
	private int pitchingModelSize = 0;

	@Getter
	@Setter
	private int hittingModelSize = 0;
	
	@Getter
	@Setter
	private Integer rPitches;

	@Getter
	@Setter
	private Integer lPitches;

	@Getter
	@Setter
	private Integer lBatters;
	@Getter
	@Setter
	private Integer rBatters;
	@Getter
	@Setter
	private Integer sBatters;

	@Getter
	@Setter
	private Double leagueDoubleRate;

	@Getter
	@Setter
	private Double leagueTripleRate;

	@Getter
	@Setter
	private String modelShortName = "Default";

	@Getter
	@Setter
	private String[] datasets;

	public static HashMap<String, OotpModel> models = new HashMap<>();

	@Getter
	@Setter
	private Integer paFilter = 1000;

	@Getter
	@Setter
	private Integer bfFilter = 1000;


	public enum StatType {
		FLOOR,
		CAP,
		CEILING;
	}

	public enum Rank {
		BEST, GOOD, BETTER, AVERAGE, WORSE, BAD, WORST;

		public static final int size;
		static {
			size = values().length;
		}
	}

	private CardStatSet[][] cardStatSets = new CardStatSet[CardStatSet.Handed.size][CardStatSet.Aggregate.size];

	public OotpModel(String displayName, String dbName, String era, String urlSegment) {
		this.displayName = displayName;
		this.dbName = dbName;
		EraSet eraSet = new EraSet();
		this.defaultEra = eraSet.getEraByName(era);
		this.urlSegment = urlSegment;
	}

	public OotpModel(String modelShortName, String[] datasets, Era era, String urlSegment) {
		this.modelShortName = modelShortName;
		this.datasets = datasets;
		this.defaultEra = era;
		this.urlSegment = urlSegment;
	}

	public CardStatSet getCardStatSet(CardStatSet.Handed h, CardStatSet.Aggregate a) {
		return this.cardStatSets[h.ordinal()][a.ordinal()];
	}

	public void setCardStatSet(CardStatSet.Handed h, CardStatSet.Aggregate a, CardStatSet cs) {
		this.cardStatSets[h.ordinal()][a.ordinal()] = cs;
	}

	public double getCardStat(CardStatSet.Handed h, CardStatSet.Aggregate a, CardStatSet.Stat stat) {
		CardStatSet css = this.cardStatSets[h.ordinal()][a.ordinal()];
		return css.getStat(stat);
	}

	public Rank getRank(int value, CardStatSet.Handed h, CardStatSet.Stat s) {
		// Assume you see 2/3 R and 1/3 L
		CardStatSet cssvl = this.cardStatSets[CardStatSet.Handed.LEFT.ordinal()][CardStatSet.Aggregate.AVG.ordinal()];
		CardStatSet cssvr = this.cardStatSets[CardStatSet.Handed.RIGHT.ordinal()][CardStatSet.Aggregate.AVG.ordinal()];
		double versus = (0.66 * cssvr.getStat(s)) + (0.33 * cssvl.getStat(s));
		double result = (value - versus) / versus;
		if (result > .2) {
			// log.info(value + ":" + result + ":" + Rank.GOOD);
			return Rank.GOOD;
		} else if (result >= 0) {
			// log.info(value + ":" + result + ":" + Rank.BETTER);
			return Rank.BETTER;
		} else if (result < -0.2) {
			// log.info(value + ":" + result + ":" + Rank.BAD);
			return Rank.BAD;
		} else {
			// log.info(value + ":" + result + ":" +Rank.WORSE);
			return Rank.WORSE;
		}
	}

	private double getExpectedHitsInPlay(Card c) {
		Double lPercent = (double) lPitches / (lPitches + rPitches);
		Double rPercent = (double) rPitches / (lPitches + rPitches);
		Double babip = Double.valueOf((c.getBABIPvL() * lPercent) + (c.getBABIPvR() * rPercent));
		if (babipFunction == null) {
			return 0.0;
		} else {
			return babipFunction.value(babip);
		}
	}

	private double getExpectedDoubles(Card c) {
		Double lPercent = (double) lPitches / (lPitches + rPitches);
		Double rPercent = (double) rPitches / (lPitches + rPitches);
		Double gap = Double.valueOf((c.getGapvL() * lPercent) + (c.getGapvR() * rPercent));
		if (doubleFunction == null) {
			return 0.0;
		} else {
			return doubleFunction.value(gap);
		}
	}

	private double getTriples() {
		return 0.007;
	}

	public double getExpectedWalks(Card c) {
		Double lPercent = (double) lPitches / (lPitches + rPitches);
		Double rPercent = (double) rPitches / (lPitches + rPitches);
		Double eye = Double.valueOf((c.getEyevL() * lPercent) + (c.getEyevR() * rPercent));
		if (walkFunction == null) {
			return 0.0;
		} else {
			return walkFunction.value(eye);
		}
	}

	public double getExpectedWalks100(Card c) {
		// log.info("poly: " + walkFunction.value(500) + " coeff:" +
		// walkFunction.toString());
		Double lPercent = (double) lPitches / (lPitches + rPitches);
		Double rPercent = (double) rPitches / (lPitches + rPitches);
		Double eye = Double.valueOf((c.getEyevL() * lPercent) + (c.getEyevR() * rPercent));
		if (walkFunction == null) {
			return 0.0;
		} else {
			return walkFunction.value(eye) * 100;
		}
	}

	public double getExpectedHomeruns(Card c) {
		// log.info("poly: " + walkFunction.value(500) + " coeff:" +
		// walkFunction.toString());
		Double lPercent = (double) lPitches / (lPitches + rPitches);
		Double rPercent = (double) rPitches / (lPitches + rPitches);
		Double power = Double.valueOf((c.getPowervL() * lPercent) + (c.getPowervR() * rPercent));
		if (homerunFunction == null) {
			return 0.0;
		} else {
			return homerunFunction.value(power);
		}
	}

	public double getExpectedHomeruns100(Card c) {
		// log.info("poly: " + walkFunction.value(500) + " coeff:" +
		// walkFunction.toString());
		Double lPercent = (double) lPitches / (lPitches + rPitches);
		Double rPercent = (double) rPitches / (lPitches + rPitches);
		Double power = Double.valueOf((c.getPowervL() * lPercent) + (c.getPowervR() * rPercent));
		if (homerunFunction == null) {
			return 0.0;
		} else {
			return homerunFunction.value(power) * 100;
		}
	}

	public double getExpectedSOs(Card c) {
		// log.info("poly: " + walkFunction.value(500) + " coeff:" +
		// walkFunction.toString());
		Double lPercent = (double) lPitches / (lPitches + rPitches);
		Double rPercent = (double) rPitches / (lPitches + rPitches);
		Double avk = Double.valueOf((c.getKsvL() * lPercent) + (c.getKsvR() * rPercent));
		if (kFunction == null) {
			return 0.0;
		} else {
			return kFunction.value(avk);
		}
	}

	private double getHitByPitch() {
		return 0.0;
	}

	private double getSacFlys() {
		return 0.0;
	}

	public double expectedOPS(Card c) {
		Double obp = 0.0;
		Double slug = 0.0;
		Double eHR = getExpectedHomeruns(c);
		Double eBB = getExpectedWalks(c);
		Double eSO = getExpectedSOs(c);
		Double eHits = getExpectedHitsInPlay(c) * (100 - eHR - eBB - eSO);
		Double eDoubles = eHits * getExpectedDoubles(c);
		obp = (eHits + eDoubles + eHR + eBB + getHitByPitch()) / 100;
		// Doubles are counted as hits and then one more time for the extra base
		slug = (eHits + eDoubles + getTriples() * 3 + eHR * 4) / 100;
		return obp + slug;
	}

	public Double getEffectiveStat(Double vL, Double vR) {
		Double lPercent = (double) lPitches / (lPitches + rPitches);
		Double rPercent = (double) rPitches / (lPitches + rPitches);
		Double stat = Double.valueOf((vL * lPercent) + (vR * rPercent));
		return stat;
	}

	public Double getEffectiveStat(Double vL, Double vR, CardStatSet.Handed hand) {
		Double totalPA = (double) this.lBatters + this.rBatters + this.sBatters;
		Double stat = 0.0;
		if (hand == CardStatSet.Handed.RIGHT) {
			stat = (((this.lBatters.doubleValue() + this.sBatters.doubleValue()) / totalPA) * vL)
					+ ((this.rBatters / totalPA) * vR);
		} else if (hand == CardStatSet.Handed.LEFT) {
			stat = (((this.lBatters) / totalPA) * vL) + (((this.rBatters + this.sBatters) / totalPA) * vR);
		} else {
			log.info("Hand: " + hand);
		}
		return stat;
	}

	public double getExpectedPWalkRate(Card c) {
		Double effectiveControl = getEffectiveStat(Double.valueOf(c.getControlvl()), Double.valueOf(c.getControlvr()),
				c.getThrows());
		if (walkFunction == null) {
			return 0.0;
		} else {
			return pWalkFunction.value(effectiveControl);
		}
	}

	public double getExpectedPHomerunRate(Card c) {
		Double effectivePower = getEffectiveStat(Double.valueOf(c.getPhrvl()), Double.valueOf(c.getPhrvr()),c.getThrows());
		if (homerunFunction == null) {
			return 0.0;
		} else {
			return pHomerunFunction.value(effectivePower);
		}
	}

	public double getExpectedPKRate(Card c) {
		Double effectiveStuff = getEffectiveStat(Double.valueOf(c.getStuffvl()), Double.valueOf(c.getStuffvr()),c.getThrows());
		if (pKFunction == null) {
			return 0.0;
		} else {
			return pKFunction.value(effectiveStuff);
		}
	}

	public double getExpectedPBabip(Card c) {
		Double effectivePBabip = getEffectiveStat(Double.valueOf(c.getPbabipvl()), Double.valueOf(c.getPbabipvr()),c.getThrows());
		if (pBabipFunction == null) {
			return 0.0;
		} else {
			return pBabipFunction.value(effectivePBabip);
		}
	}

	public double getExpectedPObp(Card c) {
		Double obp = 0.0;
		Double eHR = getExpectedPHomeruns100(c);
		Double eBB = getExpectedPWalkRate(c) * 100;
		Double eKs = getExpectedPKRate(c) * 100;
		Double eHits = getExpectedPBabip(c) * (100 - eHR - eBB - eKs);
		Double eDoubles = eHits * leagueDoubleRate;
		Double eTriples = eHits * leagueTripleRate;
		Double eSingles = eHits - eDoubles - eTriples;
		obp = (eSingles + eDoubles + eTriples + eHR + eBB + getHitByPitch())/100;
		return obp;
	}

	public double getExpectedPSlg(Card c) {
		Double slug = 0.0;
		Double eHR = getExpectedPHomerunRate(c) * 100;
		Double eBB = getExpectedPWalkRate(c) * 100;
		Double eKs = getExpectedPKRate(c) * 100;
		Double eHits = getExpectedPBabip(c) * (100 - eHR - eBB - eKs);
		Double eDoubles = eHits * leagueDoubleRate;
		Double eTriples = eHits * leagueTripleRate;
		Double eSingles = eHits - eDoubles - eTriples;
		slug = (eSingles + (eDoubles * 2) + (eTriples * 3) + (eHR * 4)) / 100;
		//slug = ( (eHR * 4)) / 100;
		//log.info("1b: " + eSingles + " 2b: " + eDoubles + " 3b: " + eTriples  + " eHR: " + eHR);
		return slug;
	}

	public double getExpectedPDoubles100(Card c){
		Double eHR = getExpectedPHomerunRate(c) * 100;
		Double eBB = getExpectedPWalkRate(c) * 100;
		Double eKs = getExpectedPKRate(c) * 100;
		Double eHits = getExpectedPBabip(c) * (100 - eHR - eBB - eKs);
		return eHits * leagueDoubleRate;
	}

	public double getExpectedPTriples100(Card c){
		Double eHR = getExpectedPHomerunRate(c) * 100;
		Double eBB = getExpectedPWalkRate(c) * 100;
		Double eKs = getExpectedPKRate(c) * 100;
		Double eHits = getExpectedPBabip(c) * (100 - eHR - eBB - eKs);
		return eHits * leagueTripleRate;
	}

	public double getExpectedPHomeruns100(Card c){
		return getExpectedPHomerunRate(c) * 100;
	}

	public double expectedOPSa(Card c) {
		return getExpectedPObp(c) + getExpectedPSlg(c);
	}
}
