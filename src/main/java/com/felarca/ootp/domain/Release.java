package com.felarca.ootp.domain;

import java.time.LocalDateTime;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Release {

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

    private static HashMap<String, Release> eras = new HashMap<String,Release>();

	static {
		eras.put("7Day",new Release("7Day", "time", LocalDateTime.now().minusDays(7), Meta.ENDOFTIME));
		eras.put("14Day", new Release("14Day", "time", LocalDateTime.now().minusDays(14), Meta.ENDOFTIME));
		eras.put("30Day",new Release("30Day", "time", LocalDateTime.now().minusDays(30), Meta.ENDOFTIME));
		eras.put("60Day",new Release("60Day", "time", LocalDateTime.now().minusDays(60), Meta.ENDOFTIME));
		eras.put("90Day",new Release("90Day", "time", LocalDateTime.now().minusDays(90), Meta.ENDOFTIME));

		eras.put("R3", new Release("R3", "content", Meta.RELEASE3, Meta.ENDOFTIME));
		eras.put("R2", new Release("R2", "content", Meta.RELEASE2, Meta.RELEASE3));
		eras.put("R1", new Release("R1", "content", Meta.RELEASE1, Meta.RELEASE2));
		eras.put("Launch", new Release("Launch", "content", Meta.LAUNCH, Meta.RELEASE1));
		eras.put("AllTime", new Release("AllTime", "time", Meta.LAUNCH, Meta.ENDOFTIME));
    }

	public static Release getReleaseByName(String name){
		return eras.get(name);
	}
}
