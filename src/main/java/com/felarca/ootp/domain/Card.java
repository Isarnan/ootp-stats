package com.felarca.ootp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
@AllArgsConstructor
public class Card {    	
	@Getter
	@Setter
	private long cid;

    @Getter
	@Setter
	private String fn;
    @Getter
	@Setter
	private String ln;

    @Getter
	@Setter
	private long stamina;

    @Getter
	@Setter
	private long stuffvl;
    @Getter
	@Setter
	private long  controlvl;
    @Getter
	@Setter
	private long phrvl;
    @Getter
	@Setter
	private long pbabipvl;
    @Getter
	@Setter
	private long  stuffvr;
    @Getter
	@Setter
	private long controlvr;
    @Getter
	@Setter
	private long phrvr;
    @Getter
	@Setter
	private long pbabipvr;
	@Setter
	private long handed;
	@Getter
	@Setter
	private long ifr;
	@Getter
	@Setter
	private long ofRange;
	@Getter
	@Setter
	private long ratingC;
	@Getter
	@Setter
	private long rating1B;
	@Getter
	@Setter
	private long rating2B;
	@Getter
	@Setter
	private long rating3B;
	@Getter
	@Setter
	private long ratingSS;
	@Getter
	@Setter
	private long ratingRF;
	@Getter
	@Setter
	private long ratingCF;
	@Getter
	@Setter
	private long ratingLF;
	@Getter
	@Setter
	private long ContactvL;
	@Getter
	@Setter
	private long GapvL;
	@Getter
	@Setter
	private long PowervL;
	@Getter
	@Setter
	private long EyevL;
	@Getter
	@Setter
	private long KsvL;
	@Getter
	@Setter
	private long BABIPvL;
	@Getter
	@Setter
	private long ContactvR;
	@Getter
	@Setter
	private long GapvR;
	@Getter
	@Setter
	private long PowervR;
	@Getter
	@Setter
	private long EyevR;
	@Getter
	@Setter
	private long KsvR;
	@Getter
	@Setter
	private long BABIPvR;
	@Getter
	@Setter
	private long overall;
	@Setter
	private long Throws;
	@Setter
	private long Bats;
	@Setter
	private long cardType;
	@Setter
	private long cardSubType;

	private String CardTitle = "Jim Unknown";

	@Getter
	@Setter
	private long ratingP = 75;

	@Getter
	@Setter
	private long owned;


	// End of DB Columns

	public enum Stat {
		EYEVL,
		EYEVR,
		POWERVL,
		POWERVR;
	}

	public void setCardTitle(String title){
		log.info("title: " + title);
		this.CardTitle = title;
	}


	public Integer getVra(){
		return (int)KsvR + (int)BABIPvR;
	}
	
	public Integer getVrp(){
		return (int)EyevR + (int)PowervR;
	}

	public Integer getVla(){
		return (int)KsvL + (int)BABIPvL;
	}

	public Integer getVlp(){
		return (int)EyevL + (int)PowervL;
	}

    public String getHanded(){
        switch((int)this.handed){
            case 1:
                return "RHP";
            case 2:
                return "LHP";
            default:
                return "WTF";

        }
    }

	public CardStatSet.Handed getThrows(){
		switch((int)this.Throws){
			case 1:
				return CardStatSet.Handed.RIGHT;
			default: 
				return CardStatSet.Handed.LEFT;
		}

	}
	public CardStatSet.Handed getBats(){
		switch((int)this.Bats){
			case 1:
				return CardStatSet.Handed.RIGHT;
			case 2:
				return CardStatSet.Handed.LEFT;
			case 3:
				return CardStatSet.Handed.SWITCH;
			default: 
				return CardStatSet.Handed.LEFT;
		}

	}

    public long getSplits(){
        return (stuffvl + controlvl + phrvl + pbabipvl) - (stuffvr + controlvr + phrvr + pbabipvr);
    }

	public long getBattingSplits(){
        return (KsvL + EyevL+ PowervL + BABIPvL) - (KsvR + EyevR + PowervR + BABIPvR);
    }
	public double getEye(){
		return (this.EyevL + this.EyevR) /2;
	}
	public double getPower(){
		return (this.PowervL + this.PowervR) /2;
	}

	public String getType(){
		return com.felarca.ootp.domain.CardType.getCardType(this.cardType, this.cardSubType);
	}
}
