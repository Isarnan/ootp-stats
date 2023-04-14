package com.felarca.ootp.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;


/**
 * The persistent class for the stats2 database table.
 * 
 */
@Data
@Entity
@Table(name="stats2")
public class Stats2 {
	
	@Id
	@Column
	private Integer cid;

	@Lob
	@Column(name="CTitle")
	private String title;
	
}