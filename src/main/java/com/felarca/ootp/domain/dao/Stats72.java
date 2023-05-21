package com.felarca.ootp.domain.dao;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="stats72")
public class Stats72 {

	
	@Id
	@Column
	private Integer cid;

	@Column
	private BigInteger singles;

	@Column
	private BigInteger p_singles;

	@Column
	private BigInteger doubles;

	@Column
	private BigInteger p_doubles;

	@Column
	private BigInteger triples;

	@Column
	private BigInteger p_triples;
	
	@Column
	private Double b_pitches;

	@Column(name="AB")
	private BigInteger ab;

	@Column
	private BigInteger p_ab;

	@Column(name="BB")
	private BigInteger bb;

	@Column
	private BigInteger p_bb;

	@Column(name="BS")
	private BigInteger bs;

	@Column(name="CG")
	private BigInteger cg;

	@Column(name="CS")
	private BigInteger cs;

	@Column
	private BigInteger p_cs;

	@Lob
	private String date;

	@Column(name="ER")
	private BigInteger er;

	@Column(name="FB")
	private BigInteger fb;

	@Column(name="G")
	private double g;

	@Column
	private BigInteger p_games;

	@Lob
	private String game;

	@Column(name="GB")
	private BigInteger gb;

	@Column(name="GIDP")
	private BigInteger gidp;

	@Column(name="GS")
	private double gs;

	@Column
	private BigInteger p_gamesstarted;

	@Column(name="H")
	private BigInteger h;

	@Column(name="HLD")
	private BigInteger hld;

	@Column(name="HP")
	private BigInteger hp;

	@Column
	private BigInteger p_hp;

	@Column(name="HR")
	private BigInteger hr;

	@Column
	private BigInteger p_homeruns;

	@Column(name="IBB")
	private BigInteger ibb;

	@Column
	private BigInteger p_ibb;

	@Column(name="ID")
	private BigInteger id;

	private BigInteger index;

	@Column(name="IP")
	private double ip;

	@Column(name="K")
	private BigInteger k;

	@Column(name="L")
	private BigInteger l;

	@Lob
	@Column(name="Name")
	private String name;

	@Column(name="OVR")
	private BigInteger ovr;

	@Column(name="PA")
	private BigInteger pa;

	@Column(name="PI")
	private BigInteger pi;

	private double pipa;

	@Lob
	@Column(name="POS")
	private String pos;

	@Column(name="PPG")
	private BigInteger ppg;

	@Column(name="QS")
	private BigInteger qs;

	@Column(name="R")
	private BigInteger r;

	@Column
	private BigInteger p_runs;

	@Column(name="RA")
	private BigInteger ra;

	@Column(name="RBI")
	private BigInteger rbi;

	@Column(name="SB")
	private BigInteger sb;

	@Column
	private BigInteger p_sb;

	@Column(name="SHO")
	private BigInteger sho;

	@Column(name="SO")
	private BigInteger so;

	@Column(name="SV")
	private BigInteger sv;

	@Lob
	@Column(name="Title")
	private String title;

	@Lob
	@Column(name="TM")
	private String tm;

	@Lob
	private String tournament_type;

	@Column(name="W")
	private BigInteger w;	
	
	@Column
	private Double innings;	

	@Column(name="SF")
	public BigInteger sf;


/*

    @Id
    public BigInteger index;
	public String date;
	public BigInteger ID;

    @Column(name="POS")
	public String pos;

	public String Name;

    @Column(name="TM")
	public String tm;
	
    public String tournament_type;
	
    @Column(name="CID")
    public BigInteger cid;
	
    public String Title;

    @Column(name="OVR")
	public BigInteger ovr;
	
    public Double G;
	public Double GS;

    @Column(name="PA")
	public BigInteger pa;
	
    @Column(name="AB")
    public BigInteger ab;

	public BigInteger H;
	public BigInteger singles;
	public BigInteger doubles;
	public BigInteger triples;

    @Column(name="HR")
	public BigInteger hr;

	public BigInteger RBI;
    
    @Column(name="R")
	public BigInteger r;

    @Column(name="BB")
	public BigInteger bb;

    @Column(name="IBB")
	public BigInteger ibb;

    @Column(name="HP")
	public BigInteger hp;

	public BigInteger SH;
	public BigInteger SF;
	public BigInteger SO;
	public BigInteger GIDP;
	public Double pipa;

    @Column(name="SB")
	public BigInteger sb;

    @Column(name="CS")
	public BigInteger cs;


	public BigInteger p_games;
	public BigInteger p_gamesstarted;

    @Column(name="W")
	public BigInteger w;
	
    @Column(name="L")
    public BigInteger l;

	public BigInteger SVO;
	public BigInteger SV;
	public BigInteger BS;
	public BigInteger HLD;
	public BigInteger SD;
	public BigInteger MD;
	public Double IP;
	public BigInteger BF;
	public BigInteger p_ab;
	public BigInteger p_singles;
	public BigInteger p_doubles;
	public BigInteger p_triples;
	public BigInteger p_homeruns;
	public BigInteger p_runs;

    @Column(name="ER")
	public BigInteger er;
	public BigInteger p_bb;
	public BigInteger p_ibb;
	public BigInteger K;
	public BigInteger p_hp;
	public BigInteger p_sh;
	public BigInteger p_sf;
	public BigInteger WP;
	public BigInteger BK;
	public BigInteger CI;
	public BigInteger DP;
	public BigInteger RA;
	public BigInteger GF;
	public BigInteger IR;
	public BigInteger IRS;
	public BigInteger QS;
	public BigInteger CG;
	public BigInteger SHO;
	public BigInteger PPG;
	public BigInteger PI;
	public BigInteger GB;
	public BigInteger FB;
	public BigInteger p_sb;
	public BigInteger p_cs;
	public Double b_pitches;
	public Double innings;
	public String game;
*/
}
