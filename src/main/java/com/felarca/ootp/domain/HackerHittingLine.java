package com.felarca.ootp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor

public class HackerHittingLine {
	@Getter
	@Setter
	private Integer cid;
	@Getter
	@Setter
	private String title;
	@Getter
	@Setter
	private long games;
	@Getter
	@Setter
	private long ab;
	

	public HackerHittingLine() {
	}

	public HackerHittingLine(Integer cid, String title, long games) {
		this.cid = cid;
		this.games = games;
		this.title = title;
	}

}
