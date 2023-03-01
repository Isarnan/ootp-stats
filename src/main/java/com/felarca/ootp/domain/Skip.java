package com.felarca.ootp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "skip")
public class Skip {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String file;

	public Skip() {
	}

	public Skip(Integer id, Integer index, String file) {
		this.id = id;
		this.file = file;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
