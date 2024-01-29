package com.felarca.ootp.domain;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class Tournament {
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
	private Integer rPitches;

	@Getter
	@Setter
	private Integer lPitches;

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

	public Tournament(String displayName, String dbName, String era, String urlSegment) {
		this.displayName = displayName;
		this.dbName = dbName;
		EraSet eraSet = new EraSet();
		this.defaultEra = eraSet.getEraByName(era);
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
		Double lPercent = (double)lPitches/(lPitches+rPitches);
		Double rPercent = (double)rPitches/(lPitches+rPitches);
		Double babip = Double.valueOf( (c.getBABIPvL() * lPercent) + (c.getBABIPvR() * rPercent ) ); 
		if(babipFunction == null){
			return 0.0;
		} else {
			return babipFunction.value(babip);
		}
	}

	private double getExpectedDoubles(Card c) {
		Double lPercent = (double)lPitches/(lPitches+rPitches);
		Double rPercent = (double)rPitches/(lPitches+rPitches);
		Double gap = Double.valueOf( (c.getGapvL() * lPercent) + (c.getGapvR() * rPercent) );
		if(doubleFunction == null){
			return 0.0;
		} else {
			return doubleFunction.value(gap);
		}
	}


	private double getTriples() {
		return 0.007;
	}

	public double getExpectedWalks100(Card c){
		//log.info("poly: " + walkFunction.value(500) + " coeff:" + walkFunction.toString());
		Double lPercent = (double)lPitches/(lPitches+rPitches);
		Double rPercent = (double)rPitches/(lPitches+rPitches);
		Double eye = Double.valueOf( (c.getEyevL() * lPercent) + (c.getEyevR() * rPercent) );
		if(walkFunction == null){
			return 0.0;
		} else {
			return walkFunction.value(eye) * 100;
		}
	}

	public double getExpectedHomeruns100(Card c){
		//log.info("poly: " + walkFunction.value(500) + " coeff:" + walkFunction.toString());
		Double lPercent = (double)lPitches/(lPitches+rPitches);
		Double rPercent = (double)rPitches/(lPitches+rPitches);
		Double power = Double.valueOf( (c.getPowervL() * lPercent) + (c.getPowervR() * rPercent) );
		if(homerunFunction == null){
			return 0.0;
		} else {
			return homerunFunction.value(power) * 100;
		}
	}

	public double getExpectedKs100(Card c){
		//log.info("poly: " + walkFunction.value(500) + " coeff:" + walkFunction.toString());
		Double lPercent = (double)lPitches/(lPitches+rPitches);
		Double rPercent = (double)rPitches/(lPitches+rPitches);
		Double avk = Double.valueOf( (c.getKsvL() * lPercent) + (c.getKsvR() * rPercent) );
		if(kFunction == null){
			return 0.0;
		} else {
			return kFunction.value(avk) * 100;
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
		Double lPercent = (double)lPitches/(lPitches+rPitches);
		Double rPercent = (double)rPitches/(lPitches+rPitches);
		Double eye = Double.valueOf( (c.getEyevL() * lPercent) + (c.getEyevR() * rPercent) );
		Double power = Double.valueOf( (c.getPowervL() * lPercent) + (c.getPowervR() * rPercent) );
		Double babip = Double.valueOf( (c.getBABIPvL() * lPercent) + (c.getBABIPvR() * rPercent ) ); 
		Double avk = Double.valueOf( (c.getKsvL() * lPercent) + (c.getKsvR() * rPercent) );
		Double gap = Double.valueOf( (c.getGapvL() * lPercent) + (c.getGapvR() * rPercent) );


		log.info("l: " + lPitches + " r: " + rPitches + "eye: " + lPercent);
		log.info("eye: " + eye + "power: " + power);
		
		Double eHR = getExpectedHomeruns100(c);
		Double eBB = getExpectedWalks100(c);
		Double eSO = getExpectedKs100(c);
		Double eHits = getExpectedHitsInPlay(c) * (100 - eHR - eBB - eSO);
		Double eDoubles = eHits * getExpectedDoubles(c);
		obp = ( eHits + eDoubles + eHR + eBB + getHitByPitch()) / 100;
		// Doubles are counted as hits and then one more time for the extra base
		slug = ( eHits + eDoubles + getTriples() * 3 + eHR* 4) / 100;
		return obp + slug;
	}
}
