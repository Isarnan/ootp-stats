package com.felarca.ootp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Deprecated

@AllArgsConstructor
public class StatsLine {
	@Setter
	@Getter
	private String type;
	
	@Setter
	@Getter
	private String team;
	
	@Setter
	@Getter
	private String time;
	
	@Setter
	@Getter
	private Hitter stats;

	public String toString() {
		return "Type: " + type + " Team: " + team + " Time: " + time;
	}
}
