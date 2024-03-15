package com.felarca.ootp.domain;

import java.time.LocalDateTime;
import java.util.HashMap;

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

    private static HashMap<String, Era> eras = new HashMap<String,Era>();

	static {
		eras.put("7Day",new Era("7Day", "time", LocalDateTime.now().minusDays(7), Meta.ENDOFTIME));
		eras.put("14Day", new Era("14Day", "time", LocalDateTime.now().minusDays(14), Meta.ENDOFTIME));
		eras.put("30Day",new Era("30Day", "time", LocalDateTime.now().minusDays(30), Meta.ENDOFTIME));
		eras.put("R3", new Era("R3", "content", Meta.RELEASE3, Meta.ENDOFTIME));
		eras.put("R2", new Era("R2", "content", Meta.RELEASE2, Meta.RELEASE3));
		eras.put("R1", new Era("R1", "content", Meta.RELEASE1, Meta.RELEASE2));
		eras.put("Launch", new Era("Launch", "content", Meta.LAUNCH, Meta.RELEASE1));
		eras.put("AllTime", new Era("AllTime", "time", Meta.LAUNCH, Meta.ENDOFTIME));
    }

	public static Era getEraByName(String name){
		return eras.get(name);
	}
}
