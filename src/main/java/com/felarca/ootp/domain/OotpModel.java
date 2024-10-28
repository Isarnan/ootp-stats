package com.felarca.ootp.domain;

import java.util.HashMap;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.springframework.beans.factory.annotation.Autowired;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.Repositories.Stats72Repository;
import com.felarca.ootp.domain.dao.Cards;
import com.felarca.ootp.domain.dao.StatYear;

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
	private Release defaultRelease;

	@Getter
	@Setter
	private String year = "2010";

	@Getter
	@Setter
	private Ballpark ballpark = Ballpark.findBallparkByName("Heinsohn Ballpark");

	@Getter
	@Setter
	private StatYear statYear;

	@Setter
	@Getter
	private String urlSegment;

	@Getter
	@Setter
	private double fipConstant = 3.2;

	@Getter
	@Setter
	private double opsCorrection = 1.8;

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
	private Integer rPitches = 0;

	@Getter
	@Setter
	private Integer lPitches = 0;

	@Getter
	@Setter
	private Integer lBatters = 0;
	@Getter
	@Setter
	private Integer rBatters = 0;
	@Getter
	@Setter
	private Integer sBatters = 0;

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

	@Getter
	@Setter
	private Integer eyeDegree = 1;

	@Getter
	@Setter
	private Integer avkDegree = 1;

	@Getter
	@Setter
	private Integer powerDegree = 1;

	@Getter
	@Setter
	private Integer babipDegree = 1;

	@Getter
	@Setter
	private Integer doubleDegree = 1;

	@Getter
	@Setter
	private Integer controlDegree = 1;

	@Getter
	@Setter
	private Integer stuffDegree = 1;

	@Getter
	@Setter
	private Integer pHRDegree = 1;

	@Getter
	@Setter
	private Integer pBabipDegree = 1;

	@Getter
	@Setter
	private Integer pDoubleDegree = 1;

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

	// Start stats about the Model
	public Double getLeftBatterPercentage(){
		if(this.lBatters + this.rBatters + this.sBatters < 1 )return 0.0;
		return this.lBatters.doubleValue() / (this.lBatters + this.rBatters + this.sBatters);
	}
	public Double getRightBatterPercentage(){
		if(this.lBatters + this.rBatters + this.sBatters < 1 )return 0.0;
		return this.rBatters.doubleValue() / (this.lBatters + this.rBatters + this.sBatters);
	}
	public Double getSwitchBatterPercentage(){
		if(this.lBatters + this.rBatters + this.sBatters < 1 )return 0.0;
		return this.sBatters.doubleValue() / (this.lBatters + this.rBatters + this.sBatters);
	}
	public Double getLeftPitchesPercentage(){
		if(this.lPitches + this.rPitches < 1 )return 0.0;
		return this.lPitches.doubleValue() / (this.lPitches + this.rPitches);
	}
	public Double getRightPitchesPercentage(){
		if(this.lPitches + this.rPitches < 1 )return 0.0;
		return this.rPitches.doubleValue() / (this.lPitches + this.rPitches);
	}
	//////////////////////////////

	private CardStatSet[][] cardStatSets = new CardStatSet[CardStatSet.Handed.size][CardStatSet.Aggregate.size];

	public OotpModel(String displayName, String dbName, String rel, String urlSegment) {
		this.displayName = displayName;
		this.dbName = dbName;
		this.defaultRelease = Release.getReleaseByName(rel);
		this.urlSegment = urlSegment;
	}

	public OotpModel(String modelShortName, String[] datasets, Release rel, String urlSegment) {
		this.modelShortName = modelShortName;
		this.datasets = datasets;
		this.defaultRelease = rel;
		this.urlSegment = urlSegment;
		this.statYear = Meta.getStatYear("2010");
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

	private double getExpectedHitsInPlay(Cards c, CardStatSet.Handed pitcherHanded) {
		if (babipFunction == null)
			return 0.0;
		Double babip = 0.0;
		if (pitcherHanded == CardStatSet.Handed.RIGHT) {
			babip = Double.valueOf(c.getBABIPvR());
		} else if (pitcherHanded == CardStatSet.Handed.LEFT) {
			babip = Double.valueOf(c.getBABIPvL());
		} else {
			Double lPercent = (double) lPitches / (lPitches + rPitches);
			Double rPercent = (double) rPitches / (lPitches + rPitches);
			babip = Double.valueOf((c.getBABIPvL() * lPercent) + (c.getBABIPvR() * rPercent));
		}
		double hip = babipFunction.value(babip);
		if (hip >= 0)
			return hip;
		else
			return 0.0;
	}

	private double getExpectedDoubles(Cards c, CardStatSet.Handed pitcherHanded) {
		if (doubleFunction == null)
			return 0.0;
		Double gap = 0.0;
		if (pitcherHanded == CardStatSet.Handed.RIGHT) {
			gap = Double.valueOf(c.getGapvR());
		} else if (pitcherHanded == CardStatSet.Handed.LEFT) {
			gap = Double.valueOf(c.getGapvL());
		} else {
			Double lPercent = (double) lPitches / (lPitches + rPitches);
			Double rPercent = (double) rPitches / (lPitches + rPitches);
			gap = Double.valueOf((c.getGapvL() * lPercent) + (c.getGapvR() * rPercent));
		}
		double doubles = doubleFunction.value(gap);
		if (doubles >= 0)
			return doubles;
		else
			return 0.0;
	}

	private double getTriples() {
		return 0.007;
	}

	public double getExpectedWalks(Cards c, CardStatSet.Handed pitcherHanded) {
		if (walkFunction == null)
			return 0.0;
		Double eye = 0.0;
		if (pitcherHanded == CardStatSet.Handed.RIGHT) {
			eye = Double.valueOf(c.getEyevR());
		} else if (pitcherHanded == CardStatSet.Handed.LEFT) {
			eye = Double.valueOf(c.getEyevL());
		} else {
			Double lPercent = (double) lPitches / (lPitches + rPitches);
			Double rPercent = (double) rPitches / (lPitches + rPitches);
			eye = Double.valueOf((c.getEyevL() * lPercent) + (c.getEyevR() * rPercent));
		}
		double walks = walkFunction.value(eye);
		if (walks >= 0)
			return walks;
		else
			return 0.0;
	}

	public double getExpectedWalks100(Cards c) {
		if (walkFunction == null)
			return 0.0;
		// log.info("poly: " + walkFunction.value(500) + " coeff:" +
		// walkFunction.toString());
		Double lPercent = (double) lPitches / (lPitches + rPitches);
		Double rPercent = (double) rPitches / (lPitches + rPitches);
		Double eye = Double.valueOf((c.getEyevL() * lPercent) + (c.getEyevR() * rPercent));
		double walks = walkFunction.value(eye) * 100;
		if (walks >= 0)
			return walks;
		else
			return 0.0;
	}

	public double getExpectedHomeruns(Cards c, CardStatSet.Handed pitcherHanded) {
		// log.info("poly: " + walkFunction.value(500) + " coeff:" +
		// walkFunction.toString());
		if (homerunFunction == null)
			return 0.0;
		Double power = 0.0;
		if (pitcherHanded == CardStatSet.Handed.RIGHT) {
			power = Double.valueOf(c.getPowervR());
		} else if (pitcherHanded == CardStatSet.Handed.LEFT) {
			power = Double.valueOf(c.getPowervL());
		} else {
			Double lPercent = (double) lPitches / (lPitches + rPitches);
			Double rPercent = (double) rPitches / (lPitches + rPitches);
			power = Double.valueOf((c.getPowervL() * lPercent) + (c.getPowervR() * rPercent));
		}

		double hr = homerunFunction.value(power);
		if (hr >= 0)
			return hr;
		else
			return 0.0;
	}

	public double getExpectedHomeruns100(Cards c) {
		// log.info("poly: " + walkFunction.value(500) + " coeff:" +
		// walkFunction.toString());
		if (homerunFunction == null)
			return 0.0;
		Double lPercent = (double) lPitches / (lPitches + rPitches);
		Double rPercent = (double) rPitches / (lPitches + rPitches);
		Double power = Double.valueOf((c.getPowervL() * lPercent) + (c.getPowervR() * rPercent));
		double hr = homerunFunction.value(power) * 100;
		if (hr >= 0)
			return hr;
		else
			return 0.0;
	}

	public double getExpectedSOs(Cards c, CardStatSet.Handed pitcherHanded) {
		// log.info("poly: " + walkFunction.value(500) + " coeff:" +
		// walkFunction.toString());
		if (kFunction == null)
			return 0.0;
		Double avk = 0.0;
		if (pitcherHanded == CardStatSet.Handed.RIGHT) {
			avk = Double.valueOf(c.getAvoidKvR());
		} else if (pitcherHanded == CardStatSet.Handed.LEFT) {
			avk = Double.valueOf(c.getAvoidKvL());
		} else {
			Double lPercent = (double) lPitches / (lPitches + rPitches);
			Double rPercent = (double) rPitches / (lPitches + rPitches);
			avk = Double.valueOf((c.getAvoidKvL() * lPercent) + (c.getAvoidKvR() * rPercent));
		}
		double so = kFunction.value(avk);
		if (so >= 0)
			return so;
		else
			return 0.0;
	}

	private double getHitByPitch() {
		return 0.0;
	}

	private double getSacFlys() {
		return 0.0;
	}

	public double expectedCombOPS(Cards c, StatAdjustment adj) {
		Cards d = new Cards();
		d.setAvoidKvL(combAdjust(c.getAvoidKvL()));
		d.setAvoidKvR(combAdjust(c.getAvoidKvR()));
		d.setBABIPvL(combAdjust(c.getBABIPvL()));
		d.setBABIPvR(combAdjust(c.getBABIPvR()));
		d.setPowervL(combAdjust(c.getPowervL()));
		d.setPowervR(combAdjust(c.getPowervR()));
		d.setEyevL(combAdjust(c.getEyevL()));
		d.setEyevR(combAdjust(c.getEyevR()));
		d.setGapvL(combAdjust(c.getGapvL()));
		d.setGapvR(combAdjust(c.getGapvR()));
		return expectedOPS(d, adj);
	}

	public double expectedOPS(Cards c, StatAdjustment adj) {
		Double obp = 0.0;
		Double slug = 0.0;
		Double eHR = getExpectedHomeruns(c, CardStatSet.Handed.SWITCH) * adj.getHomerunAdjustment();
		Double eBB = getExpectedWalks(c, CardStatSet.Handed.SWITCH) * adj.getWalkAdjustment();
		Double eSO = getExpectedSOs(c, CardStatSet.Handed.SWITCH) * adj.getStrikeoutAdjustment();
		Double eHits = getExpectedHitsInPlay(c, CardStatSet.Handed.SWITCH) * (100 - eHR - eBB - eSO) * adj.getBabipAdjustment();
		Double eDoubles = eHits * getExpectedDoubles(c, CardStatSet.Handed.SWITCH);
		obp = (eHits + eDoubles + eHR + eBB + getHitByPitch()) / 100;
		// Doubles are counted as hits and then one more time for the extra base
		slug = (eHits + eDoubles + getTriples() * 3 + eHR * 4) / 100;
		return obp + slug;
	}

	public double expectedCombOPS(Cards c, CardStatSet.Handed pitcherHanded, StatAdjustment adj) {
		Cards d = new Cards();
		d.setAvoidKvL(combAdjust(c.getAvoidKvL()));
		d.setAvoidKvR(combAdjust(c.getAvoidKvR()));
		d.setBABIPvL(combAdjust(c.getBABIPvL()));
		d.setBABIPvR(combAdjust(c.getBABIPvR()));
		d.setPowervL(combAdjust(c.getPowervL()));
		d.setPowervR(combAdjust(c.getPowervR()));
		d.setEyevL(combAdjust(c.getEyevL()));
		d.setEyevR(combAdjust(c.getEyevR()));
		d.setGapvL(combAdjust(c.getGapvL()));
		d.setGapvR(combAdjust(c.getGapvR()));
		return expectedOPS(d, pitcherHanded, adj);
	}
	public double expectedOPS(Cards c, CardStatSet.Handed pitcherHanded, StatAdjustment adj) {
		Double obp = 0.0;
		Double slug = 0.0;
		Double eHR = getExpectedHomeruns(c, pitcherHanded);
		Double eBB = getExpectedWalks(c, pitcherHanded);
		Double eSO = getExpectedSOs(c, pitcherHanded);
		Double eHits = getExpectedHitsInPlay(c, pitcherHanded) * (100 - eHR - eBB - eSO);
		Double eDoubles = eHits * getExpectedDoubles(c, pitcherHanded);
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

	public Double getEffectiveStatSP(Double vL, Double vR, CardStatSet.Handed hand) {
		Double stat = 0.0;
		if (hand == CardStatSet.Handed.RIGHT) {
			stat = ( (6/9.0) * vL) + ( (3/9.0) * vR);
		} else if (hand == CardStatSet.Handed.LEFT) {
			stat = ( (1/9.0) * vL) + ( (8/9.0) * vR);
		} else {
			log.info("Hand: " + hand);
		}
		return stat;
	}


	/*
	 * Pitcher stuff
	 */

	public double getExpectedPWalkRate(Cards c, String pType) {
		Double effectiveControl = 0.0;
		if(pType != null && pType.equals("SP")){
			effectiveControl = getEffectiveStatSP(Double.valueOf(c.getControlvL()), Double.valueOf(c.getControlvR()),				c.getThrows());
		}else {
		 	effectiveControl = getEffectiveStat(Double.valueOf(c.getControlvL()), Double.valueOf(c.getControlvR()),				c.getThrows());
		}
		if (walkFunction == null)
			return 0.0;
		double walks = pWalkFunction.value(effectiveControl);
		if (walks > 0)
			return walks;
		else
			return 0.0;
	}

	public double getExpectedPHomerunRate(Cards c, String pType) {
		Double effectivePower = 0.0;
		if(pType != null && pType.equals("SP")){
			effectivePower = getEffectiveStatSP(Double.valueOf(c.getPHRvL()), Double.valueOf(c.getPHRvR()),c.getThrows());
		} else {
			effectivePower = getEffectiveStat(Double.valueOf(c.getPHRvL()), Double.valueOf(c.getPHRvR()),c.getThrows());
		}
		if (pHomerunFunction == null)
			return 0.0;
		double hr = pHomerunFunction.value(effectivePower);
		if (hr >= 0)
			return hr;
		else
			return 0.0;

	}

	public double getExpectedPKRate(Cards c, String pType) {

		Double effectiveStuff =0.0;
		if(pType != null && pType.equals("SP")){
			effectiveStuff = getEffectiveStatSP(Double.valueOf(c.getStuffvL()), Double.valueOf(c.getStuffvR()),c.getThrows());
		}else {
			effectiveStuff = getEffectiveStat(Double.valueOf(c.getStuffvL()), Double.valueOf(c.getStuffvR()),c.getThrows());
		}
		if (pKFunction == null)
			return 0.0;
		double k = pKFunction.value(effectiveStuff);
		if (k >= 0)
			return k;
		else
			return 0.0;
	}

	public double getExpectedPBabip(Cards c, String pType) {
		Double effectivePBabip =0.0;
		if(pType != null && pType.equals("SP")){
			effectivePBabip = getEffectiveStatSP(Double.valueOf(c.getPBABIPvL()), Double.valueOf(c.getPBABIPvR()),c.getThrows());
		}else{
			effectivePBabip = getEffectiveStat(Double.valueOf(c.getPBABIPvL()), Double.valueOf(c.getPBABIPvR()),c.getThrows());
		}
		if (pBabipFunction == null)
			return 0.0;
		double pbabip = pBabipFunction.value(effectivePBabip);
		if (pbabip >= 0)
			return pbabip;
		else
			return 0.0;
	}

	public double getExpectedPObp(Cards c, String pType, StatAdjustment adj) {
		Double obp = 0.0;
		Double eHR = getExpectedPHomerunRate(c, pType) *100 * adj.getHomerunAdjustment();
		Double eBB = getExpectedPWalkRate(c, pType) * 100 * adj.getWalkAdjustment();
		Double eKs = getExpectedPKRate(c, pType) * 100 * adj.getStrikeoutAdjustment();
		Double eHits = getExpectedPBabip(c, pType) * (100 - eHR - eBB - eKs) * adj.getBabipAdjustment();
		Double eDoubles = eHits * leagueDoubleRate;
		Double eTriples = eHits * leagueTripleRate;
		Double eSingles = eHits - eDoubles - eTriples;
		obp = (eSingles + eDoubles + eTriples + eHR + eBB + getHitByPitch()) / 100;
		return obp;
	}

	public double getExpectedPSlg(Cards c, String pType, StatAdjustment adj) {
		Double slug = 0.0;
		Double eHR = getExpectedPHomerunRate(c, pType) * 100 * adj.getHomerunAdjustment();
		Double eBB = getExpectedPWalkRate(c, pType) * 100 * adj.getWalkAdjustment();
		Double eKs = getExpectedPKRate(c, pType) * 100 * adj.getStrikeoutAdjustment();
		Double eHits = getExpectedPBabip(c, pType) * (100 - eHR - eBB - eKs) * adj.getBabipAdjustment();
		Double eDoubles = eHits * leagueDoubleRate;
		Double eTriples = eHits * leagueTripleRate;
		Double eSingles = eHits - eDoubles - eTriples;
		slug = (eSingles + (eDoubles * 2) + (eTriples * 3) + (eHR * 4)) / 100;
		// slug = ( (eHR * 4)) / 100;
		// log.info("1b: " + eSingles + " 2b: " + eDoubles + " 3b: " + eTriples + " eHR:
		// " + eHR);
		return slug;
	}

	public double getExpectedPDoubles100(Cards c, String pType) {
		Double eHR = getExpectedPHomerunRate(c, pType) * 100;
		Double eBB = getExpectedPWalkRate(c, pType) * 100;
		Double eKs = getExpectedPKRate(c, pType) * 100;
		Double eHits = getExpectedPBabip(c, pType) * (100 - eHR - eBB - eKs);
		return eHits * leagueDoubleRate;
	}

	public double getExpectedPTriples100(Cards c, String pType) {
		Double eHR = getExpectedPHomerunRate(c, pType) * 100;
		Double eBB = getExpectedPWalkRate(c, pType) * 100;
		Double eKs = getExpectedPKRate(c, pType) * 100;
		Double eHits = getExpectedPBabip(c, pType) * (100 - eHR - eBB - eKs);
		return eHits * leagueTripleRate;
	}

	/*
	public double getExpectedPHomeruns100(Cards c) {
		return getExpectedPHomerunRate(c) * 100;
	}
	*/


	public double expectedOPSa(Cards c, String pType, StatAdjustment adj ) {
		// Added 1.8 to get closer to WOBA
		return (getExpectedPObp(c, pType, adj) * opsCorrection )+ getExpectedPSlg(c, pType, adj);
	}

	
	public double expectedCombOPSa(Cards c, String pType, StatAdjustment adj ) {
		Cards d = new Cards();
		d.setPHRvL(combAdjust(c.getPHRvL()));
		d.setPHRvR(combAdjust(c.getPHRvR()));

		d.setPBABIPvL(combAdjust(c.getPBABIPvL()));
		d.setPBABIPvR(combAdjust(c.getPBABIPvR()));

		d.setControlvL(combAdjust(c.getControlvL()));
		d.setControlvR(combAdjust(c.getControlvR()));

		d.setStuffvL(combAdjust(c.getStuffvL()));
		d.setStuffvR(combAdjust(c.getStuffvR()));

		d.setPHRvL(combAdjust(c.getPHRvL()));
		d.setPHRvR(combAdjust(c.getPHRvR()));

		return (getExpectedPObp(d, pType, adj) * opsCorrection )+ getExpectedPSlg(d, pType, adj);
	}


	public double getExpectedFIP(Cards c){
		double fip = 0.0;
		Double eHR = getExpectedPHomerunRate(c, "P") * 100;
		Double eBB = getExpectedPWalkRate(c, "P") * 100;
		Double eKs = getExpectedPKRate(c, "P") * 100;
		/* Need to get IP */
		fip = (13 * eHR + 3*(eBB) - 2*eKs);
		return fip;		
	}

	public long combAdjust(long num){
		long newVal = num;
		if(num < 15){
			newVal = num + 6;
		} else if ( num >= 15 && num < 30){
			newVal = num + 7;
		}else if ( num >= 30 && num < 45){
			newVal = num + 8;
		}else if ( num >= 45 && num < 60){
			newVal = num + 9;
		}else if ( num >= 60 && num < 75){
			newVal = num + 10;
		}else if ( num >= 75 && num < 90){
			newVal = num + 11;
		}else if ( num >= 90 && num < 115){
			newVal = num + 12;
		}else if ( num >= 115 && num < 135){
			newVal = num + 13;
		}else if ( num >= 135 && num < 155){
			newVal = num + 13;
		}else if ( num >= 155 && num < 175){
			newVal = num + 13;
		}else if ( num >= 175 && num < 195){
			newVal = num + 13;
		}else if ( num >= 195 && num < 215){
			newVal = num + 13;
		}else if ( num >= 215 && num < 235){
			newVal = num + 13;
		}
		return newVal;
	}

	public static OotpModel getTournamentByDbName(String tournamenttype) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getTournamentByDbName'");
	}
}
