package com.felarca.ootp.domain;

import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.annotations.Formula;

import ch.qos.logback.classic.Logger;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.AccessLevel;


@Log
@RequiredArgsConstructor
public class Hitter {
	@Getter
	@Setter
	private int cid;
	@Getter
	@Setter
	private Integer owned;
	@NonNull
	@Getter
	@Setter
	private String card;
	@NonNull
	@Setter
	private String pos;
	@NonNull
	@Getter
	@Setter
	private Double ovr;
	@NonNull
	@Setter
	@Getter
	private BigInteger pa;
	@NonNull
	@Getter
	@Setter
	private Double b_pitches;
	@NonNull
	@Getter
	@Setter
	private BigInteger singles;
	@NonNull
	@Getter
	@Setter
	private BigInteger doubles;
	@NonNull
	@Getter
	@Setter
	private BigInteger triples;
	@NonNull
	@Getter
	@Setter
	private BigInteger hr;
	@NonNull
	@Getter
	@Setter
	private BigInteger bb;
	@NonNull
	@Getter
	@Setter
	private BigInteger ibb;
	@NonNull
	@Getter
	@Setter
	private BigInteger hp;	
	@Setter
	private double pipa;
	@Setter
	private double obp;
	@Setter
	private double slug;
	@Setter
	private double ops;
	@NonNull
	@Getter
	@Setter
	private Double innings;
	@NonNull
	@Getter
	@Setter
	private BigInteger er;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_singles;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_doubles;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_triples;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_homeruns;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_bb;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_ibb;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_hp;	
	@NonNull
	@Getter
	@Setter
	private BigInteger ab;	
	@NonNull
	@Getter
	@Setter
	private BigInteger sb;	
	@NonNull
	@Getter
	@Setter
	private BigInteger cs;	
	@NonNull
	@Getter
	@Setter
	private BigInteger k;
	@NonNull
	@Getter
	@Setter
	private Double g;
	@NonNull
	@Getter
	@Setter
	private Double gs;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_games;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_gamesstarted;
	
	public Double getEra() {
		if(innings != 0 )return (er.doubleValue()/innings) * 9;
		else return 0.0;
	}
	public Double getWhip() {
		if(innings != 0 )return (p_singles.doubleValue() + p_doubles.doubleValue() + p_triples.doubleValue() + p_homeruns.doubleValue() + p_bb.doubleValue() + p_ibb.doubleValue() + p_hp.doubleValue())/innings.doubleValue();
		else return 0.0;
	}
	
	public Double getIp() {
		return this.innings;
	}
	public String getPos() {
		this.pos = Stream.of(this.pos.split(",")).distinct().collect(Collectors.joining(","));
		return this.pos;
	}
	public double getPipa() {
		this.pipa = this.b_pitches / this.pa.doubleValue();
		return this.pipa;		
	}
	public double getObp() {
		this.obp = (this.singles.doubleValue() + this.doubles.doubleValue() + this.triples.doubleValue() + this.hr.doubleValue() + this.bb.doubleValue() + this.ibb.doubleValue() + this.hp.doubleValue()) / this.pa.doubleValue();
		return this.obp;
	}
	public double getSlug() {
		this.slug = (this.singles.doubleValue() + this.doubles.doubleValue()*2 + this.triples.doubleValue()*3 + this.hr.doubleValue()*4 ) / this.ab.doubleValue();
		return this.slug;
	}
	public double getOps() {
		this.ops = (this.getSlug() + this.getObp());
		return this.ops;
	}
	
	// Steals
	public double getSbp600() {
		return (this.sb.doubleValue() / this.pa.doubleValue()) * 600;
	}
	public double getCsp600() {
		return (this.cs.doubleValue() / this.pa.doubleValue()) * 600;
	}
	public double getNetSbP600() {
		return (((this.sb.doubleValue() - this.cs.doubleValue())/this.pa.doubleValue())*600);
	}
	public double getSbPercent() {
		if(this.sb.doubleValue()+this.cs.doubleValue() == 0.00)return 0.00;
		return ((this.sb.doubleValue() /  (this.sb.doubleValue() + this.cs.doubleValue()))*100);
	}
	
	
	public double getKp9() {
		return (this.k.doubleValue() / this.innings ) * 9;
	}
	public double getBbp9() {
		return (this.p_bb.doubleValue() / this.innings ) * 9;
	}
	public double getHrp9() {
		return (this.p_homeruns.doubleValue() / this.innings ) * 9;
	}
	public double getIbbp9() {
		return (this.p_ibb.doubleValue() / this.innings ) * 9;
	}
	public double getBabip() {
		//MIssing SF from denominator
		return (this.singles.doubleValue() + this.doubles.doubleValue() + this.triples.doubleValue())/(this.ab.doubleValue() - this.k.doubleValue() - this.hr.doubleValue());		
	}
	public double getPBabip() {
		//MIssing SF from denominator, need pa or ab for pitchers		
		//return (this.p_singles.doubleValue() + this.p_doubles.doubleValue() + this.p_triples.doubleValue())/(this.p.doubleValue() - this.k.doubleValue() - this.hr.doubleValue());
		return 0.0;
	}
}
