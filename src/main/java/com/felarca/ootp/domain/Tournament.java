package com.felarca.ootp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor

public class Tournament {

	@Setter
	@Getter
	private String displayName;
	
	@Setter
	@Getter
	private String dbName;
	
	@Setter
	@Getter
	private String defaultEra;

	@Setter
	@Getter
	private String urlSegment;
}
