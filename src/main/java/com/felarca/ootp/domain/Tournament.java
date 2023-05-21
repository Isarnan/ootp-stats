package com.felarca.ootp.domain;

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

	public Rank getRank(int value, CardStatSet.Handed h, CardStatSet.Stat s){
		// Assume you see 2/3 R and 1/3 L
		CardStatSet cssvl = this.cardStatSets[CardStatSet.Handed.LEFT.ordinal()][CardStatSet.Aggregate.AVG.ordinal()];
		CardStatSet cssvr = this.cardStatSets[CardStatSet.Handed.RIGHT.ordinal()][CardStatSet.Aggregate.AVG.ordinal()];
		double versus = (0.66 * cssvr.getStat(s)) + (0.33 * cssvl.getStat(s));
		double result = (value - versus)/versus;
		if(result > .2 ){
			log.info(value + ":" + result + ":" + Rank.GOOD);
			return Rank.GOOD;
		}else if (result >= 0 ){
			log.info(value + ":" + result + ":" + Rank.BETTER);
			return Rank.BETTER;
		} else if (result < -0.2){
			log.info(value + ":" + result + ":" + Rank.BAD);
			return Rank.BAD;
		} else {
			log.info(value + ":" + result + ":" +Rank.WORSE);
			return Rank.WORSE;
		}
	}
	// Used to feed the color displays
	// type is floor, cap, cieling
	public int getThreshhold(StatType type, Card.Stat stat) {
		if (urlSegment.equals("bronze")) {
			switch (stat) {
				case EYEVL:
					switch (type) {
						case CAP:
							return 70;
						case CEILING:
							return 70;
						case FLOOR:
							return 70;
						default:
							return 70;
					}
				case EYEVR:
					break;
				case POWERVL:
					break;
				case POWERVR:
					break;
				default:
					break;

			}
		}

		return 0;
	}
}
