package com.felarca.ootp.domain.dao;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.felarca.ootp.domain.CardStatSet;

import lombok.Data;
import lombok.extern.java.Log;

@Log
@Data
@Entity
@Table(name="`cards`")
public class Cards {
	//private long index;
	@Column(name="cardtitle")
	private String cardTitle;

	@Id
	@Column(name="cardID")
	private long cardID;
	@Column(name="Overall")
	private long overall;

	@Column(name="cardtype")
	private long cardType;
	@Column(name="cardsubtype")
	private long cardSubType;
	@Column(name="year")
	private long year;
	private String Peak;
	private String Team;
	private String ln;
	private String fn;
	@Column(name="nickname")
	private String nickName;
	@Column(name="uniformnumber")
	private long uniformNumber;
	private long DayOB;
	private long MonthOB;
	private long YearOB;
	private long Bats;
	private long Throws;
	private long Position;
	@Column(name="pitcherrole")
	private long pitcherRole;
	private long Contact;
	private long Gap;
	private long Power;
	private long Eye;
	@Column(name="avoidks")
	private long avoidK;
	private long BABIP;
	private long ContactvL;
	private long GapvL;
	private long PowervL;
	private long EyevL;
	@Column(name="KsvL")
	private long avoidKvL;
	private long BABIPvL;
	private long ContactvR;
	private long GapvR;
	private long PowervR;
	private long EyevR;
	@Column(name="avoidkvr")
	private long avoidKvR;
	private long BABIPvR;
	@Column(name="gbhittertype")
	private long gBHitterType;
	@Column(name="fbhittertype")
	private long fBHitterType;
	@Column(name="battedballtype")
	private long battedBallType;
	private long Speed;
	private long Stealing;
	private long Baserunning;
	private long Sacbunt;
	private long Buntforhit;
	private long Stuff;
	private long Movement;
	@Column(name="control")
	private long control;
	private long pHR;
	private long pBABIP;
	private long StuffvL;
	private long MovementvL;
	private long ControlvL;
	private long pHRvL;
	private long pBABIPvL;
	private long StuffvR;
	private long MovementvR;
	private long ControlvR;
	private long pHRvR;
	private long pBABIPvR;
	private long Fastball;
	private long Slider;
	private long Curveball;
	private long Changeup;
	private long Cutter;
	private long Sinker;
	private long Splitter;
	private long Forkball;
	private long Screwball;
	private long Circlechange;
	private long Knucklecurve;
	private long Knuckleball;
	private long Stamina;
	private long Hold;
	private long GB;
	private String Velocity;
	@Column(name="armslot")
	private long armSlot;
	private long Height;
	private long ifr;
	@Column(name="infielderror")
	private long infieldError;
	@Column(name="infieldarm")
	private long infieldArm;
	private long DP;
	@Column(name="catcherabil")
	private long catcherAbil;
	@Column(name="catcherframe")
	private long catcherFrame;
	@Column(name="catcherarm")
	private long catcherArm;
	@Column(name="ofrange")
	private long ofRange;
	private long OFError;
	private long OFArm;
	@Column(name="posratingp")
	private long ratingP;
	private long ratingC;
	private long rating1B;
	private long rating2B;
	private long rating3B;
	private long ratingSS;
	private long ratingLF;
	private long ratingCF;
	private long ratingRF;
	private long LearnC;
	private long Learn1B;
	private long Learn2B;
	private long Learn3B;
	private long LearnSS;
	private long LearnLF;
	private long LearnCF;
	private long LearnRF;
	@Column(name="buyorderhigh")
	private long buyOrderHigh;
	@Column(name="sellorderlow")
	private long sellOrderLow;
	@Column(name="last10price")
	private long last10Price;
	@Column(name="`buyorderhigh(cc)`")
	private long ccBuyOrderHigh;
	@Column(name="`sellorderlow(cc)`")
	private long ccSellOrderLow;
	@Column(name="`last10price(cc)`")
	private long ccLast10Price;
	private long era;
	private long tier;
	@Column(name="missionvalue")
	private long missionValue;
	private long limit;
	private long owned;
	//private double Unnamed111;


		public enum Stat {
		EYEVL,
		EYEVR,
		POWERVL,
		POWERVR;
	}

	public void setCardTitle(String title){
		log.info("title: " + title);
		this.cardTitle = title;
	}


	public Integer getVra(){
		return (int)avoidKvR + (int)BABIPvR;
	}
	
	public Integer getVrp(){
		return (int)EyevR + (int)PowervR;
	}

	public Integer getVla(){
		return (int)avoidKvL + (int)BABIPvL;
	}

	public Integer getVlp(){
		return (int)EyevL + (int)PowervL;
	}

    public String getHanded(){
        switch((int)this.Throws){
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
        return (StuffvL + ControlvL + pHRvL + pBABIPvL) - (StuffvR + ControlvR + pHRvR + pBABIPvR);
    }

	public long getBattingSplits(){
        return (avoidKvL + EyevL+ PowervL + BABIPvL) - (avoidKvR + EyevR + PowervR + BABIPvR);
    }
	public double getEye(){
		return (this.EyevL + this.EyevR) /2;
	}
	public double getPower(){
		return (this.PowervL + this.PowervR) /2;
	}

	public String getType(){
		return com.felarca.ootp.domain.CardType.toString(this.cardType, this.cardSubType);
	}
}