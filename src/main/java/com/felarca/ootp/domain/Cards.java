package com.felarca.ootp.domain;

import javax.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="cards")
public class Cards {
	
	@Id
	@Column(name="CardID")
	private Integer cid;

	@Lob
	@Column(name="Bats")
	private String bats;
	
}