package com.felarca.ootp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@AllArgsConstructor
@Table(name = "hackerpitching")
@Log
public class HackerPitching {
	@Id
	@Getter
	@Setter
	@Column
	private Integer cid;
	@Getter
	@Setter
	@Column
	private String card;
	@Getter
	@Setter
	@Column
	private long games;
	@Getter
	@Setter
	@Column
	private long gs;
	@Getter
	@Setter
	@Column
	private long er;
	@Getter
	@Setter
	@Column
	private long ip;
	@Getter
	@Setter	
	@Column(name="1b")
	private long singles;
	@Getter
	@Setter
	@Column(name="2b")
	private long doubles;
	@Getter
	@Setter
	@Column(name="3b")
	private long triples;
	@Getter
	@Setter
	@Column
	private long hr;
	@Getter
	@Setter
	@Column
	private long bb;
	@Getter
	@Setter
	@Column
	private long ibb;
	@Getter
	@Setter
	@Column
	private long hp;
	@Getter
	@Setter
	@Column
	private long k;
	@Getter
	@Setter
	@Formula(value = "(er / ip) * 9")
	private double era;
	@Getter
	@Setter
	@Formula(value = "(1b +2b + 3b + hr + bb + ibb + hp) / ip")
	private double whip;

	public HackerPitching() {
		super();
	}

/*
	public double getPipa() {
		this.pipa = (double) pitches / pa;
		log.finest("PIPA: " + pipa);
		return this.pipa;
	}

	public double getObp() {
		this.obp = (double) (this.singles + this.doubles + this.triples + this.hr + this.bb + this.ibb + this.hp) / this.pa;
		return this.obp;
	}
	*/
}
