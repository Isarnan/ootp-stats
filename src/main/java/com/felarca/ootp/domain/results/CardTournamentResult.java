package com.felarca.ootp.domain.results;

import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.felarca.ootp.domain.LinearWeights;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;


@Log
@RequiredArgsConstructor
public class CardTournamentResult {
	
	@NonNull
	@Getter
	@Setter
	private Integer cid;
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
	@NonNull
	@Getter
	@Setter
	private BigInteger p_pitches;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_fb;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_gb;
	@NonNull
	@Getter
	@Setter
	private BigInteger p_runs;
	@NonNull
	@Getter
	@Setter
	private BigInteger sf;
	@NonNull
	@Getter
	@Setter
	private BigInteger so;

// End of contructor

	@Setter
	private double pipa;
	@Setter
	private double obp;
	@Setter
	private double slug;
	@Setter
	private double ops;

	@Setter
	@Getter
	private double expectedBBRate = 0;

	@Setter
	@Getter
	private double expectedPWalkRate = 0;

	@Setter
	@Getter
	private double expectedHomerunRate = 0;

	@Setter
	@Getter
	private double expectedPHomerunRate = 0;

	@Setter
	@Getter
	private double expectedKRate = 0;

	@Setter
	@Getter
	private double expectedPKRate = 0;

	@Setter
	@Getter
	private double expectedPBabip = 0;

	@Setter
	@Getter
	private double expectedOPS = 0;

	@Setter
	@Getter
	private double expectedOPSa = 0;
	
	@Setter
	@Getter
	private double expectedPObp = 0;

	@Setter
	@Getter
	private double expectedPSlg = 0;


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
		//this.pipa = this.b_pitches / this.pa.doubleValue();
		return this.b_pitches.doubleValue() / this.pa.doubleValue();		
	}
	public double getPpipa() {
		//this.ppipa = this.p_pitches / (p_singles.doubleValue() + p_doubles.doubleValue() + p_triples.doubleValue() + p_homeruns.doubleValue() + p_bb.doubleValue() + p_ibb.doubleValue() + p_hp.doubleValue());
		return this.p_pitches.doubleValue() / (p_singles.doubleValue() + p_doubles.doubleValue() + p_triples.doubleValue() + p_homeruns.doubleValue() + p_bb.doubleValue() + p_ibb.doubleValue() + p_hp.doubleValue() + p_fb.doubleValue() + p_gb.doubleValue() + k.doubleValue());		
	}
	public double getPig() {
		//this.ppipa = this.p_pitches / (p_singles.doubleValue() + p_doubles.doubleValue() + p_triples.doubleValue() + p_homeruns.doubleValue() + p_bb.doubleValue() + p_ibb.doubleValue() + p_hp.doubleValue());
		return this.p_pitches.doubleValue() / this.p_games.doubleValue();
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
		return (this.singles.doubleValue() + this.doubles.doubleValue() + this.triples.doubleValue())/(this.ab.doubleValue() - this.so.doubleValue() - this.hr.doubleValue() + this.getSf().doubleValue());		
	}
	public double getPBabip() {
		return (this.p_singles.doubleValue() + this.p_doubles.doubleValue() + this.p_triples.doubleValue())/(this.getBattersFaced() - this.p_bb.doubleValue() - this.p_homeruns.doubleValue() - this.k.doubleValue());
		//return 0.0;
	}
	public double getRunsPerGame(){
		// log.info("Runs: " + this.p_runs + "GS: " + this.p_gamesstarted + "ER: " + this.er);
		log.info("R/PA: " + this.p_runs.doubleValue() / this.pa.doubleValue());
		return this.p_runs.doubleValue() / this.p_gamesstarted.doubleValue();
	}
	public double getWoba(){
		// (unintentional BB factor x unintentional BB + HBP factor x HBP + 1B factor x 1B + 2B factor x 2B + 3B factor x 3B + HR factor x HR)/(AB + unintentional BB + SF + HBP)
		double numerator = (LinearWeights.ubbFactor * this.bb.doubleValue()) 
			+ (LinearWeights.hbpFactor * this.hp.intValue()) 
			+ (LinearWeights.singleFactor * this.singles.intValue()) 
			+ (LinearWeights.doubleFactor * this.doubles.intValue()) 
			+ (LinearWeights.tripleFactor * this.triples.intValue()) 
			+ (LinearWeights.hrFactor * this.hr.intValue());
		int denominator = this.pa.intValue();
		return numerator / denominator;
	}

	public double getStrikeoutRate(){
		return 100* (this.so.doubleValue() / this.pa.doubleValue());
	}
	public double getPKRate(){
		return (this.k.doubleValue() / this.getBattersFaced());
	}

	public double getWalkRate(){
		return 100 * (this.bb.doubleValue() / this.pa.doubleValue());
	}
	public double getPWalkRate(){
		return (this.p_bb.doubleValue() / this.getBattersFaced());
	}

	public double getHomerunRate(){
		return (this.hr.doubleValue() / this.pa.doubleValue());
	}
	public double getPHomerunRate(){
		return (this.p_homeruns.doubleValue() / this.getBattersFaced());
	}




	public double getFip(double fipConstant){
		return ((13.0*this.p_homeruns.doubleValue())+(3.0*(this.p_bb.doubleValue()+this.p_hp.doubleValue()))-(2*this.so.doubleValue()))/this.innings + fipConstant;
	}
	public double getRawFip(){
		return ((13.0*this.p_homeruns.doubleValue())+(3.0*(this.p_bb.doubleValue()+this.p_hp.doubleValue()))-(2*this.so.doubleValue()))/this.innings;
	}
	public int getBattersFaced(){
		int outs = this.getP_gb().intValue() + this.getP_fb().intValue() + this.getK().intValue();
		int onBase = this.getP_singles().intValue() + this.getP_doubles().intValue() + this.getP_triples().intValue() + this.getP_homeruns().intValue() + this.getP_bb().intValue();
		return outs + onBase;
	}

	public double getPAb(){
		//This isnt right
		int outs = this.getP_gb().intValue() + this.getP_fb().intValue() + this.getK().intValue();
		int hits = this.getP_singles().intValue() + this.getP_doubles().intValue() + this.getP_triples().intValue() + this.getP_homeruns().intValue();

		return outs + hits;

	}

	public double getPObp() {
		this.obp = (this.p_singles.doubleValue() + this.p_doubles.doubleValue() + this.p_triples.doubleValue() + this.p_homeruns.doubleValue() + this.p_bb.doubleValue() + this.p_ibb.doubleValue() + this.p_hp.doubleValue()) / this.getBattersFaced();
		return this.obp;
	}
	public double getPSlg() {
		this.slug = (this.p_singles.doubleValue() + this.p_doubles.doubleValue()*2 + this.p_triples.doubleValue()*3 + this.p_homeruns.doubleValue()*4 ) / this.getBattersFaced();
		//this.slug = ( this.p_homeruns.doubleValue()*4 ) / this.getBattersFaced();
		return this.slug;
	}
	public double getPOps() {
		this.ops = (this.getPSlg() + this.getPObp());
		return this.ops;
	}
}
