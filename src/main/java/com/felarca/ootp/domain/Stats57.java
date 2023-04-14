package com.felarca.ootp.domain;
import javax.persistence.*;
import lombok.Data;
import java.math.BigInteger;


/**
 * The persistent class for the stats57 database table.
 * 
 */
@Data
@Entity
@Table(name="stats57")
public class Stats57 {
	
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
	
}