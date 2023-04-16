package com.felarca.ootp.domain;

import javax.persistence.*;
import lombok.Data;

/**
 * The persistent class for the stats2 database table.
 * This is a 2 column view of all cards I own.
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