package com.felarca.ootp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;

import com.felarca.ootp.domain.Ballpark;
import com.felarca.ootp.domain.CardStatSet;
import com.felarca.ootp.domain.CardType;
import com.felarca.ootp.domain.Meta;
import com.felarca.ootp.domain.OotpModel;
import com.felarca.ootp.domain.Release;
import com.felarca.ootp.domain.Restriction;
import com.felarca.ootp.domain.Tournament;
import com.felarca.ootp.domain.dao.StatYear;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class OotpApplication {

	public static void main(String[] args) {
		SpringApplication.run(OotpApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		System.out.println("hello world, I have just started up");

		try {
			File file = new ClassPathResource("static/csv/years.csv").getFile();
			CSVReader csvReader = new CSVReader(new FileReader(file.toPath().toString()));
			Meta.statYears = new CsvToBeanBuilder<StatYear>(csvReader).withType(StatYear.class).withSkipLines(1).build()
					.parse();

		} catch (FileNotFoundException ex) {
			System.out.println(ex);

		} catch (IOException ex) {
			System.out.println(ex);
		}

		/*
		 * Default Tournaments
		 */
		Restriction restrict;
		Tournament tournament;
		String name = "LiveTournament";
		CardType type = CardType.getCardType("Live");
		ArrayList<CardType> types = new ArrayList<>();
		types.add(type);
		restrict = new Restriction(null, 40, 100, 1800, 2100, types);
		tournament = new Tournament(name, restrict, Meta.getStatYear("2010"),Ballpark.findBallparkByName("Heinsohn Ballpark"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 70);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 75);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 85);
		tournament.addFilter(CardStatSet.Position.LEFTFIELD, CardStatSet.DefensiveStat.OUTFIELDRANGE, 60);
		tournament.addFilter(CardStatSet.Position.CENTERFIELD, CardStatSet.DefensiveStat.OUTFIELDRANGE, 80);
		tournament.addFilter(CardStatSet.Position.RIGHTFIELD, CardStatSet.DefensiveStat.OUTFIELDRANGE, 60);
		Tournament.tournaments.put(name, tournament);

		name = "LeagueTournament";
		tournament = new Tournament(name,
				new Restriction(null, 40, 110, 1800, 2100, null), Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Hanbat Baseball Stadium"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 80);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 90);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 95);
		Tournament.tournaments.put(name, tournament);

		name = "NegroLeagueTournament";
		type = CardType.getCardType("Negro League");
		types = new ArrayList<>();
		types.add(type);
		type = CardType.getCardType("Negro League 2");
		types.add(type);
		restrict = new Restriction(null, 40, 110, 1800, 2100, types);
		tournament = new Tournament(name, restrict, Meta.getStatYear("2010"), Ballpark.findBallparkByName("Heinsohn Ballpark"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 80);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 90);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 95);
		Tournament.tournaments.put(name, tournament);

		name = "PerfectDraftTournament";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 40, 110, 1800, 2100, null), Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "NearlyPerfectTournament";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 95, 110, 1800, 2100, null), Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "DiamondTournament";
		tournament = new Tournament(name, new Restriction(null, 40, 99, 1800, 2100, null), Meta.getStatYear("2010"), Ballpark.findBallparkByName("Heinsohn Ballpark"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 80);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 90);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 95);
		Tournament.tournaments.put(name, tournament);

		name = "LowDiamondTournament";
		tournament = new Tournament(name, new Restriction(null, 40, 94, 1800, 2100, null), Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 80);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 90);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 95);
		Tournament.tournaments.put(name, tournament);

		name = "GoldTournament";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 40, 89, 1800, 2100, null), Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "SilverTournament";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 40, 79, 1800, 2100, null), Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "BronzeTournament";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 40, 69, 1800, 2100, null), Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "IronTournament";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 40, 59, 1800, 2100, null), Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "DiamondLiveTournament";
		type = CardType.getCardType("Live");
		types = new ArrayList<>();
		types.add(type);
		restrict = new Restriction(null, 40, 99, 1800, 2100, types);
		Tournament.tournaments.put(name, new Tournament(name, restrict, Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "LowDiamondLiveTournament";
		type = CardType.getCardType("Live");
		types = new ArrayList<>();
		types.add(type);
		restrict = new Restriction(null, 40, 94, 1800, 2100, types);
		Tournament.tournaments.put(name, new Tournament(name, restrict, Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "GoldLiveTournament";
		type = CardType.getCardType("Live");
		types = new ArrayList<>();
		types.add(type);
		restrict = new Restriction(null, 40, 89, 1800, 2100, types);
		Tournament.tournaments.put(name, new Tournament(name, restrict, Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "SilverLiveTournament";
		type = CardType.getCardType("Live");
		types = new ArrayList<>();
		types.add(type);
		restrict = new Restriction(null, 40, 79, 1800, 2100, types);
		Tournament.tournaments.put(name, new Tournament(name, restrict, Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "BronzeLiveTournament";
		type = CardType.getCardType("Live");
		types = new ArrayList<>();
		types.add(type);
		restrict = new Restriction(null, 40, 69, 1800, 2100, types);
		Tournament.tournaments.put(name, new Tournament(name, restrict, Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "OOTPTournament";
		tournament = new Tournament(name,new Restriction(null, 40, 110, 1999, 2100, null), Meta.getStatYear("2010"), Ballpark.findBallparkByName("Heinsohn Ballpark"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 80);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 90);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 95);
		Tournament.tournaments.put(name, tournament);


		name = "WedPreOOTPTournament";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 80, 99, 1800, 1999, null), Meta.getStatYear("1987"),
				Ballpark.findBallparkByName("Wrigley Field")));

		name = "1910to1959";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 40, 110, 1910, 1959, null), Meta.getStatYear("1951"),
				Ballpark.findBallparkByName("Seals Stadium")));

		name = "HeinsohnTournament";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 40, 110, 1970, 2100, null), Meta.getStatYear("1977"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "TuesdayIceTournament";
		tournament = new Tournament(name, new Restriction(null, 90, 99, 1800, 2100, null), Meta.getStatYear("1993"),
				Ballpark.findBallparkByName("Veterans Stadium"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 80);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 90);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 95);
		Tournament.tournaments.put(name, tournament);

		name = "GoldRushTournament";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 80, 89, 1800, 2100, null), Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "Mayhem";
		Tournament.tournaments.put(name, new Tournament(name,
				new Restriction(null, 40, 102, 2024, 2024, null), Meta.getStatYear("2010"),
				Ballpark.findBallparkByName("Heinsohn Ballpark")));

		name = "PTCSDiamond";
		tournament = new Tournament(name,
		new Restriction(null, 40, 99, 1800, 2025, null), Meta.getStatYear("1922"),	Ballpark.findBallparkByName("Polo Grounds"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 80);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 90);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 95);
		Tournament.tournaments.put(name, tournament);
		
		name = "PTCSOpen";
		tournament = new Tournament(name,
		new Restriction(null, 40, 102, 1800, 2025, null), Meta.getStatYear("1922"),	Ballpark.findBallparkByName("Polo Grounds"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 80);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 90);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 95);
		Tournament.tournaments.put(name, tournament);

		name = "PTCSWildcard";
		tournament = new Tournament(name,
		new Restriction(null, 65, 94, 1800, 1949, null), Meta.getStatYear("1922"),	Ballpark.findBallparkByName("Polo Grounds"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 80);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 90);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 95);
		Tournament.tournaments.put(name, tournament);

		name = "PTMSNearlyPerfect";
		tournament = new Tournament(name,
				new Restriction(null, 95, 110, 1800, 2100, null), Meta.getStatYear("1962"),
				Ballpark.findBallparkByName("Candlestick Park"));
		tournament.addFilter(CardStatSet.Position.CATCHER, CardStatSet.DefensiveStat.CATCHERFRAMING, 80);
		tournament.addFilter(CardStatSet.Position.SECONDBASE, CardStatSet.DefensiveStat.INFIELDRANGE, 90);
		tournament.addFilter(CardStatSet.Position.SHORTSTOP, CardStatSet.DefensiveStat.INFIELDRANGE, 95);
		Tournament.tournaments.put(name, tournament);

		/*
		 * name = "CoversTournament";
		 * types = (ArrayList<CardType>)CardType.getTypes().clone();
		 * type = CardType.getCardType("Live");
		 * types.remove(type);
		 * restrict = new Restriction(null, 40, 100, 1800, 2100, types);
		 * Tournament.tournaments.put(name, new Tournament(name, restrict,
		 * Meta.getStatYear("1988"),
		 * Ballpark.findBallparkByName("Heinsohn Ballpark")));
		 */

		/*
		 * Default Models
		 */

		name = "LiveModel";
		OotpModel oMod = new OotpModel(name, new String[] { "LiveQuick" }, Release.getReleaseByName("AllTime"), null);
		oMod.setBfFilter(3000);
		oMod.setPaFilter(3000);
		oMod.setPowerDegree(2);
		oMod.setEyeDegree(2);
		oMod.setAvkDegree(2);
		oMod.setBabipDegree(2);
		oMod.setControlDegree(3);
		oMod.setPHRDegree(2);
		oMod.setStuffDegree(3);
		OotpModel.models.put(name, oMod);

		name = "OpenModel";
		oMod = new OotpModel(name, new String[] { "Open" }, Release.getReleaseByName("60Day"), null);
		oMod.setBfFilter(2000);
		oMod.setPaFilter(2000);
		oMod.setPowerDegree(2);
		oMod.setAvkDegree(2);
		oMod.setBabipDegree(2);
		oMod.setControlDegree(2);
		oMod.setPHRDegree(2);
		oMod.setStuffDegree(2);
		OotpModel.models.put(name, oMod);

		name = "BronzeModel";
		oMod = new OotpModel(name, new String[] { "Bronze" }, Release.getReleaseByName("AllTime"), null);
		oMod.setBfFilter(1000);
		oMod.setPaFilter(1000);
		OotpModel.models.put(name, oMod);

		name = "GoldModel";
		oMod = new OotpModel(name, new String[] { "Gold" }, Release.getReleaseByName("AllTime"), null);
		oMod.setBfFilter(100);
		oMod.setPaFilter(100);
		OotpModel.models.put(name, oMod);

		
		name = "LowDiamondModel";
		oMod = new OotpModel(name, new String[] { "LowDiamond" }, Release.getReleaseByName("60Day"), null);
		oMod.setBfFilter(2000);
		oMod.setPaFilter(2000);
		oMod.setPowerDegree(2);
		oMod.setAvkDegree(2);
		oMod.setBabipDegree(2);
		oMod.setControlDegree(3);
		oMod.setPHRDegree(3);
		oMod.setStuffDegree(3);
		OotpModel.models.put(name, oMod);

		name = "DiamondModel";
		oMod = new OotpModel(name, new String[] { "Diamond" }, Release.getReleaseByName("AllTime"), null);
		oMod.setBfFilter(3000);
		oMod.setPaFilter(3000);
		oMod.setPowerDegree(2);
		oMod.setAvkDegree(2);
		oMod.setBabipDegree(2);
		oMod.setDoubleDegree(2);
		oMod.setControlDegree(2);
		oMod.setPHRDegree(2);
		oMod.setStuffDegree(2);
		OotpModel.models.put(name, oMod);

		name = "PerfectDraftModel";
		oMod = new OotpModel(name, new String[] { "StandardPerfectDraft" }, Release.getReleaseByName("AllTime"), null);
		oMod.setPaFilter(3000);
		oMod.setBfFilter(3000);
		oMod.setAvkDegree(2);
		oMod.setBabipDegree(2);
		oMod.setDoubleDegree(2);
		oMod.setPowerDegree(2);
		oMod.setControlDegree(2);
		oMod.setPHRDegree(2);
		oMod.setStuffDegree(2);
		OotpModel.models.put(name, oMod);

		name = "LeagueModel";
		oMod = new OotpModel(name, new String[] { "DiamondLeague", "LeaguevL", "LeaguevR" },
				Release.getReleaseByName("60Day"),
				null);
		oMod.setBfFilter(2000);
		oMod.setPaFilter(2000);
		oMod.setPowerDegree(2);
		oMod.setAvkDegree(2);
		oMod.setControlDegree(2);
		oMod.setPHRDegree(3);
		OotpModel.models.put(name, oMod);
	}
}
