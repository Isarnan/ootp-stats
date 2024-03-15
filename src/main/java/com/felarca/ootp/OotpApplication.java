package com.felarca.ootp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.felarca.ootp.domain.Era;
import com.felarca.ootp.domain.OotpModel;
import com.felarca.ootp.domain.Restriction;
import com.felarca.ootp.domain.Tournament;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class OotpApplication {

	public static void main(String[] args) {
		SpringApplication.run(OotpApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		System.out.println("hello world, I have just started up");
		/*
		 * Default Tournaments
		 */
		String name = "PerfectoTournament";
		Tournament.tournaments.put(name, new Tournament("PerfectoTournament",
				new Restriction(null, 100, 100, 1800, 2100, null), 2010, "Default"));

		name = "HighHeatTournament";
		Tournament.tournaments.put(name, new Tournament("HighHeatTournament",
				new Restriction(null, 90, 100, 1800, 2100, null), 2010, "Default"));

		name = "PerfectDraftTournament";
		Tournament.tournaments.put(name, new Tournament("PerfectDraftTournament",
				new Restriction(null, 40, 100, 1800, 2100, null), 2010, "Default"));

		/*
		 * Default Models
		 */

		name = "TestModel";
		OotpModel.models.put(name,
				new OotpModel(name, new String[] { "PERFECTO", "LIVE" }, Era.getEraByName("AllTime"), null));

		name = "PerfectoModel";
		OotpModel oMod = new OotpModel(name, new String[] { "PERFECTO" }, Era.getEraByName("AllTime"), null);
		oMod.setBfFilter(7000);
		oMod.setPaFilter(8000);
		OotpModel.models.put(name, oMod);

		name = "LiveModel";
		OotpModel.models.put(name, new OotpModel(name, new String[] { "LiveOpen" }, Era.getEraByName("AllTime"), null));

		name = "PerfectDraft";
		oMod = new OotpModel(name, new String[] { "PerfectDraft" }, Era.getEraByName("AllTime"), null);
		oMod.setBfFilter(8000);
		oMod.setPaFilter(6000);
		OotpModel.models.put(name, oMod);
	}
}
