package com.felarca.ootp.domain;


import javax.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="`cards`")
public class Cards {
	/*
	@Id
	@Column(name="CardID")
	private Integer cid;

	@Lob
	@Column(name="Bats")
	private String bats;

	@Lob
	@Column(name="tier")
	private BigInteger tier;
	
	@Lob
	@Column(name="Position")
	private BigInteger position;
	*/

	private long index;

	private String CardTitle;

	@Id
	private long CardID;
	private long CardValue;
	private long CardType;
	private long CardSubType;
	private String Peak;
	private String Team;
	private String ln;
	private String fn;
	private String NickName;
	private long UniformNumber;
	private long DayOB;
	private long MonthOB;
	private long YearOB;
	private long Bats;
	private long Throws;
	private long Position;
	private long PitcherRole;
	private long Contact;
	private long Gap;
	private long Power;
	private long Eye;
	private long AvoidKs;
	private long BABIP;
	private long ContactvL;
	private long GapvL;
	private long PowervL;
	private long EyevL;
	private long KsvL;
	private long BABIPvL;
	private long ContactvR;
	private long GapvR;
	private long PowervR;
	private long EyevR;
	private long KsvR;
	private long BABIPvR;
	private long GBHitterType;
	private long FBHitterType;
	private long BattedBallType;
	private long Speed;
	private long Stealing;
	private long Baserunning;
	private long Sacbunt;
	private long Buntforhit;
	private long Stuff;
	private long Movement;
	private long Control;
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
	private long ArmSlot;
	private long Height;
	private long ifr;
	private long InfieldError;
	private long InfieldArm;
	private long DP;
	private long CatcherAbil;
	private long CatcherArm;
	private long OFRange;
	private long OFError;
	private long OFArm;
	private long PosRatingP;
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
	private long BuyOrderHigh;
	private long SellOrderLow;
	private long Last10Price;
	private long era;
	private long tier;
	private long MissionValue;
	private long limit;
	private double Unnamed111;
}