package com.felarca.ootp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Deprecated
@Entity
@AllArgsConstructor
@Table(name = "goldhitting")
@Log
public class GoldHitting {
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
	private long atbats;
	@Getter
	@Setter
	@Column
	private long pa;
	@Getter
	@Setter
	@Column
	private long pitches;
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
	private String pos;
	@Getter
	@Setter
	@Formula(value = "pitches / pa")
	private double pipa;
	@Getter
	@Setter
	@Formula(value = "(1b +2b + 3b + hr + bb + ibb + hp) / pa")
	private double obp;

	public GoldHitting() {
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
