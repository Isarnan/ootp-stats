package com.felarca.ootp.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Era {

	@Setter
	@Getter
	private String name;
	
	@Setter
	@Getter
	private String type;
	
	@Setter
	@Getter
	private LocalDateTime start;
	
	@Setter
	@Getter
	private LocalDateTime end;

}
