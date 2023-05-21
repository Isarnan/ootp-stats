package com.felarca.ootp.domain;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class EraSet {

    private HashMap<String, Era> eras = new HashMap<String, Era>();

    public EraSet(){
		this.eras.put("7Day",new Era("7Day", "time", LocalDateTime.now().minusDays(7), Meta.ENDOFTIME));
		this.eras.put("14Day", new Era("14Day", "time", LocalDateTime.now().minusDays(14), Meta.ENDOFTIME));
		this.eras.put("30Day",new Era("30Day", "time", LocalDateTime.now().minusDays(30), Meta.ENDOFTIME));
		this.eras.put("R3", new Era("R3", "content", Meta.RELEASE3, Meta.ENDOFTIME));
		this.eras.put("R2", new Era("R2", "content", Meta.RELEASE2, Meta.RELEASE3));
		this.eras.put("R1", new Era("R1", "content", Meta.RELEASE1, Meta.RELEASE2));
		this.eras.put("Launch", new Era("Launch", "content", Meta.LAUNCH, Meta.RELEASE1));
		this.eras.put("AllTime", new Era("AllTime", "time", Meta.LAUNCH, Meta.ENDOFTIME));
    }

    public Era getEraByName(String name){
        return eras.get(name);
    }
    
}
