package com.felarca.ootp.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.felarca.ootp.Repositories.GoldHackerHittingRepository;
import com.felarca.ootp.Repositories.GoldHackerPitchingRepository;
import com.felarca.ootp.Repositories.GoldHittingRepository;
import com.felarca.ootp.Repositories.GoldPitchingRepository;
import com.felarca.ootp.Repositories.HackerHittingRepository;
import com.felarca.ootp.Repositories.HackerPitchingRepository;
import com.felarca.ootp.Repositories.HittingRepository;
import com.felarca.ootp.Repositories.PitchingRepository;
import com.felarca.ootp.Repositories.SkipRepository;
import com.felarca.ootp.Repositories.Stats57Repository;
import com.felarca.ootp.Repositories.Stats2Repository;
import com.felarca.ootp.domain.Era;
import com.felarca.ootp.domain.Hitter;
import com.felarca.ootp.domain.Meta;
import com.felarca.ootp.domain.Player;
import com.felarca.ootp.domain.Record;
import com.felarca.ootp.domain.Skip;
import com.felarca.ootp.domain.Stats57;
import com.felarca.ootp.domain.Tournament;

import lombok.extern.java.Log;

@Log
@Controller

public class HomeController {
	@Autowired
	SkipRepository skipRepo;
	@Autowired
	HittingRepository hittingRepo;
	@Autowired
	HackerHittingRepository hackerHittingRepo;
	@Autowired
	PitchingRepository pitchingRepo;
	@Autowired
	HackerPitchingRepository hackerPitchingRepo;
	@Autowired
	GoldHittingRepository goldHittingRepo;
	@Autowired
	GoldHackerHittingRepository goldHackerHittingRepo;
	@Autowired
	GoldPitchingRepository goldPitchingRepo;
	@Autowired
	GoldHackerPitchingRepository goldHackerPitchingRepo;
	@Autowired
	Stats57Repository stats57Repo;
	@Autowired
	Stats2Repository stats2Repo;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("skips", skipRepo.findAll());
		for (Skip skip : skipRepo.findAll()) {
			log.finest("Skip index: " + skip.getId());
		}
		return "index";
	}

	@RequestMapping("/team/{tournamenttype}/pitchers")
	public String hackerPitchers(Model model, @PathVariable String tournamenttype, @RequestParam(required = false) String strIp,
			@RequestParam(required = false) String time, @RequestParam(required = false) String team) {
		Meta meta = new Meta(tournamenttype);
		Tournament tournie = meta.getTournamentByName(tournamenttype);
		Era era = meta.getEraByName(time);
		log.info("Era: " + era.getName() + " Tournament: " + tournamenttype+ " DbName: " + tournie.getDbName() + " Start: " +  era.getStart()+ " end: " +  era.getEnd());
//		if (tournamenttype.equals("bronze"))
//			tournamenttype = "Bronze32";
//		else if (tournamenttype.equals("perfectteam"))
//			tournamenttype = "PerfectTeam";
//		else if (tournamenttype.equals("perfectdraft"))
//			tournamenttype = "PerfectDraft";
//		else if (tournamenttype.equals("gold"))
//			tournamenttype = "Gold32";

		// List<Hitter> list = stats57Repo.getHackerHittersList(tournamenttype);
		if (time == null)
			time = tournie.getDefaultEra();
//		if (time == null && (tournamenttype.equals("Gold32") || tournamenttype.equals("Bronze32")))
//			time = "14Day";
//		else if (time == null && (tournamenttype.equals("PerfectTeam") || tournamenttype.equals("PerfectDraft")))
//			time = "alltime";

		List<Hitter> list = stats57Repo.getTeamHittersList(tournie.getDbName(), team, era.getEnd(), era.getStart());

//		if (time != null && time.equals("R2"))
//			list = stats57Repo.getTeamHittersList(tournie.getDbName(), team, era.getEnd(), era.getStart());
//		else if (time.equals("R1"))
//			list = stats57Repo.getTeamHittersList(tournie.getDbName(), team, Meta.RELEASE2, Meta.RELEASE1);
//		else if (time.equals("7Day"))
//			list = stats57Repo.getTeamHittersList(tournie.getDbName(), team, Meta.ENDOFTIME, LocalDateTime.now().minusDays(7));
//		else if (time.equals("14Day"))
//			list = stats57Repo.getTeamHittersList(tournie.getDbName(), team, Meta.ENDOFTIME, LocalDateTime.now().minusDays(14));
//		else if (time.equals("30Day"))
//			list = stats57Repo.getTeamHittersList(tournie.getDbName(), team, Meta.ENDOFTIME, LocalDateTime.now().minusDays(30));
//		else
//			list = stats57Repo.getTeamHittersList(tournie.getDbName(), team, Meta.ENDOFTIME, Meta.LAUNCH);

		long ip = 30;
		if (strIp != null)
			ip = (long) Long.parseLong(strIp);
		// Filtering
		Predicate<Hitter> byIp = hitter -> hitter.getInnings().intValue() > 2;
		list = list.stream().filter(byIp).collect(Collectors.toList());
		// Sorting
		list.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getEra)));
		model.addAttribute("meta", meta);
		model.addAttribute("pitchers", list);
		return "pitchers";
	}

	@RequestMapping("/team/{tournamenttype}/hitters")
	public String hackerHitters(Model model, @PathVariable String tournamenttype, @RequestParam(required = false) String sort,
			@RequestParam(required = false) String filter, @RequestParam(required = false) String time, @RequestParam(required = false) String team) {
		if (tournamenttype.equals("bronze"))
			tournamenttype = "Bronze16";
		else if (tournamenttype.equals("perfectteam"))
			tournamenttype = "PerfectTeam";
		else if (tournamenttype.equals("perfectdraft"))
			tournamenttype = "PerfectDraft";
		else if (tournamenttype.equals("gold"))
			tournamenttype = "Gold32";
		else if (tournamenttype.equals("iron"))
			tournamenttype = "Iron16";

		if (time == null && (tournamenttype.equals("Gold32") || tournamenttype.equals("Bronze16")))
			time = "R2";
		else if (time == null && (tournamenttype.equals("PerfectTeam") || tournamenttype.equals("PerfectDraft")))
			time = "alltime";

		log.info("TT:" + tournamenttype + " Time: " + time + "Team: " + team);

		List<Hitter> list;
		Hitter metaHitter;

		if (time != null && time.equals("R2")) {
			list = stats57Repo.getTeamHittersList(tournamenttype, team, Meta.RELEASE3, Meta.RELEASE2);
			metaHitter = stats57Repo.getMetaTeamHitter(tournamenttype, team, Meta.RELEASE3, Meta.RELEASE2);
		} else if (time.equals("R1")) {
			list = stats57Repo.getTeamHittersList(tournamenttype, team, Meta.RELEASE2, Meta.RELEASE1);
			metaHitter = stats57Repo.getMetaTeamHitter(tournamenttype, team, Meta.RELEASE2, Meta.RELEASE1);
		} else if (time.equals("R3")) {
			list = stats57Repo.getTeamHittersList(tournamenttype, team, Meta.ENDOFTIME, Meta.RELEASE3);
			metaHitter = stats57Repo.getMetaTeamHitter(tournamenttype, team, Meta.ENDOFTIME, Meta.RELEASE3);
		} else if (time.equals("7Day")) {
			list = stats57Repo.getTeamHittersList(tournamenttype, team, Meta.ENDOFTIME, LocalDateTime.now().minusDays(7));
			metaHitter = stats57Repo.getMetaTeamHitter(tournamenttype, team, Meta.ENDOFTIME, LocalDateTime.now().minusDays(7));
		} else if (time.equals("14Day")) {
			list = stats57Repo.getTeamHittersList(tournamenttype, team, Meta.ENDOFTIME, LocalDateTime.now().minusDays(14));
			metaHitter = stats57Repo.getMetaTeamHitter(tournamenttype, team, Meta.ENDOFTIME, LocalDateTime.now().minusDays(14));
		} else if (time.equals("30Day")) {
			list = stats57Repo.getTeamHittersList(tournamenttype, team, Meta.ENDOFTIME, LocalDateTime.now().minusDays(30));
			metaHitter = stats57Repo.getMetaTeamHitter(tournamenttype, team, Meta.ENDOFTIME, LocalDateTime.now().minusDays(30));
		} else {
			list = stats57Repo.getTeamHittersList(tournamenttype, team, Meta.ENDOFTIME, Meta.LAUNCH);
			metaHitter = stats57Repo.getMetaTeamHitter(tournamenttype, team, Meta.ENDOFTIME, Meta.LAUNCH);
			log.info(team + tournamenttype + Meta.ENDOFTIME + Meta.LAUNCH);
			// List<Hitter> list = stats57Repo.getHackerHittersList(tournamenttype);
		}
		Predicate<Hitter> byPa = hitter -> hitter.getPa().intValue() > 3;
		Predicate<Hitter> byPipa = hitter -> hitter.getPipa() > 4.2;
		if (filter != null && filter.equals("pipa"))
			list = list.stream().filter(byPipa).collect(Collectors.toList());
		else
			list = list.stream().filter(byPa).collect(Collectors.toList());

		/** Sorting **/
		if ((sort == null && (tournamenttype.equals("Gold32") || tournamenttype.equals("Bronze16") || tournamenttype.equals("PerfectTeam") || tournamenttype.equals("PerfectDraft"))) || (sort != null && sort.equals("ops")))
			list.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getOps).reversed()));
		else if ((sort == null && ( tournamenttype.equals("Iron16"))) || (sort != null && sort.equals("obp")))
			list.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getObp).reversed()));
		else if (sort != null && sort.equals("pa"))
			list.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getPa).reversed()));
		log.info("Card: " + metaHitter.getCard());

		model.addAttribute("metaHitter", metaHitter);
		model.addAttribute("hitters", list);

		if (tournamenttype.equals("PerfectDraft")) {
			return "drafthitters";
		} else {
			return "hitters";
		}
	}

	@RequestMapping("/{tournamenttype}/hitters")
	public String bronzeHitter(Model model, @PathVariable String tournamenttype, @RequestParam(required = false) String sort,
			@RequestParam(required = false) String filter, @RequestParam(required = false) Integer pa,
			@RequestParam(required = false) String strPipa, @RequestParam(required = false) String time) {
		

		if (tournamenttype.equals("bronze"))
			tournamenttype = "Bronze16";
		else if (tournamenttype.equals("perfectteam"))
			tournamenttype = "PerfectTeam";
		else if (tournamenttype.equals("perfectdraft"))
			tournamenttype = "PerfectDraft";
		else if (tournamenttype.equals("gold"))
			tournamenttype = "Gold32";
		else if (tournamenttype.equals("iron"))
			tournamenttype = "Iron16";
		Meta meta = new Meta(tournamenttype);

		
		if (time == null && (tournamenttype.equals("Gold32") || tournamenttype.equals("Bronze16") || tournamenttype.equals("Iron16" )))
			time = "LAUNCH";
		else if (time == null && (tournamenttype.equals("PerfectTeam") || tournamenttype.equals("PerfectDraft")))
			time = "alltime";

		List<Hitter> list;

		if (time != null && time.equals("R2"))
			list = stats57Repo.getHittersList(tournamenttype, Meta.ENDOFTIME, Meta.RELEASE2);
		else if (time.equals("R1"))
			list = stats57Repo.getHittersList(tournamenttype, Meta.RELEASE2, Meta.RELEASE1);
		else if (time.equals("14Day"))
			list = stats57Repo.getHittersList(tournamenttype, Meta.ENDOFTIME, LocalDateTime.now().minusDays(14));
		else if (time.equals("30Day"))
			list = stats57Repo.getHittersList(tournamenttype, Meta.ENDOFTIME, LocalDateTime.now().minusDays(30));
		else
			list = stats57Repo.getHittersList(tournamenttype, Meta.ENDOFTIME, Meta.LAUNCH);

		if(pa != null){			
			Predicate<Hitter> byPa = hitter -> hitter.getPa().intValue() > pa.intValue();
			list = list.stream().filter(byPa).collect(Collectors.toList());
		} else {
			Predicate<Hitter> byPa = hitter -> hitter.getPa().intValue() > 100;
			list = list.stream().filter(byPa).collect(Collectors.toList());
		}
		// if (strPa != null)
		// 	pa = (long) Long.parseLong(strPa);
		// Predicate<Hitter> byPa = hitter -> hitter.getPa().intValue() > 30;
		// Predicate<Hitter> byPa100 = hitter -> hitter.getPa().intValue() > 100;
		// Predicate<Hitter> byPa1000 = hitter -> hitter.getPa().intValue() > 1000;
		// Predicate<Hitter> byPipa = hitter -> hitter.getPipa() > 4.3;
		// if (filter != null && filter.equals("pipa"))
		// 	list = list.stream().filter(byPipa).collect(Collectors.toList());
		// if (filter != null && filter.equals("pa1000"))
		// 	list = list.stream().filter(byPa1000).collect(Collectors.toList());
		// else if (filter != null && filter.equals("pa100"))
		// 	list = list.stream().filter(byPa100).collect(Collectors.toList());
		// else
		// 	list = list.stream().filter(byPa).collect(Collectors.toList());

		if (sort == null || sort.equals("ops"))
			list.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getOps).reversed()));
		else
			list.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getObp).reversed()));

		model.addAttribute("meta", meta);
		model.addAttribute("hitters", list);
		log.info(tournamenttype);
		log.info("Size: " + list.size());
		
		if (tournamenttype.equals("PerfectDraft")) {
			return "drafthitters";
		} else {
			return "hitters";
		}
	}

	@RequestMapping("/{tournamenttype}/pitchers")
	public String universalPitchers(Model model, @PathVariable String tournamenttype, @RequestParam(required = false) Integer ip, @RequestParam(required = false) Integer pig,
			@RequestParam(required = false) String filter, @RequestParam(required = false) String time) {
		
		Meta meta = new Meta(tournamenttype);
		Tournament tournie = meta.getTournamentByName(tournamenttype);
		if (time == null)
			time = tournie.getDefaultEra();		
		Era era = meta.getEraByName(time);
		//log.info("Era: " + time + "Tournament: " + tournamenttype);


		List<Hitter> list = stats57Repo.getHittersList(tournie.getDbName(), era.getEnd(), era.getStart());;

		//log.info("ip: " + ip);
		if(ip != null){			
			Predicate<Hitter> byIp = hitter -> hitter.getInnings().intValue() > ip.intValue();
			list = list.stream().filter(byIp).collect(Collectors.toList());
		} else {
			Predicate<Hitter> byIp = hitter -> hitter.getInnings().intValue() > 30;
			list = list.stream().filter(byIp).collect(Collectors.toList());
		}
		if(pig != null){			
			Predicate<Hitter> byPig = hitter -> Double.valueOf(hitter.getPig()) > pig.intValue();
			list = list.stream().filter(byPig).collect(Collectors.toList());
		} else {
			Predicate<Hitter> byPig = hitter -> Double.valueOf(hitter.getPig()) > 1;
			list = list.stream().filter(byPig).collect(Collectors.toList());
		}
		// Filtering
		/*
		Predicate<Hitter> byIp = hitter -> hitter.getInnings().intValue() > 30;
		Predicate<Hitter> byIp10 = hitter -> hitter.getInnings().intValue() > 10;
		Predicate<Hitter> byIp100 = hitter -> hitter.getInnings().intValue() > 100;
		Predicate<Hitter> byIp200 = hitter -> hitter.getInnings().intValue() > 200;
		if (filter != null && filter.equals("ip200")) {
			list = list.stream().filter(byIp200).collect(Collectors.toList());
		} else if (filter != null && filter.equals("ip100")) {
			list = list.stream().filter(byIp100).collect(Collectors.toList());
		} else if (tournamenttype.equals("PerfectDraft")) {
			list = list.stream().filter(byIp10).collect(Collectors.toList());
		} else {
			list = list.stream().filter(byIp).collect(Collectors.toList());
		}
		*/
		// Sorting
		list.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getEra)));
		// list.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getInnings)));
		model.addAttribute("meta", meta);
		model.addAttribute("pitchers", list);
		if (tournie.getDisplayName().equals("PerfectDraft")) {
			return "draftpitchers";
		} else {
			return "pitchers";
		}
	}

	@RequestMapping("/{tournamenttype}/meta")
	public String universalMeta(Model model, @PathVariable String tournamenttype) {
		if (tournamenttype.equals("bronze"))
			tournamenttype = "Bronze16";
		else if (tournamenttype.equals("perfectteam"))
			tournamenttype = "PerfectTeam";
		else if (tournamenttype.equals("perfectdraft"))
			tournamenttype = "PerfectDraft";
		else if (tournamenttype.equals("gold"))
			tournamenttype = "Gold32";
		else if (tournamenttype.equals("iron"))
			tournamenttype = "Iron16";

		Meta meta = new Meta(tournamenttype);
		/*
		 * meta.setHackerRecordOverall(stats57Repo.getRecord(tournamenttype,
		 * "Dark Web Hackers", Meta.ENDOFTIME, Meta.LAUNCH));
		 * meta.setHackerRecord14Day(stats57Repo.getRecord(tournamenttype,
		 * "Dark Web Hackers", Meta.ENDOFTIME, LocalDateTime.now().minusDays(14)));
		 * meta.setHackerRecord30Day(stats57Repo.getRecord(tournamenttype,
		 * "Dark Web Hackers", Meta.ENDOFTIME, LocalDateTime.now().minusDays(30)));
		 * meta.setHackerRecordR1(stats57Repo.getRecord(tournamenttype,
		 * "Dark Web Hackers", Meta.RELEASE2, Meta.RELEASE1));
		 * meta.setHackerRecordR2(stats57Repo.getRecord(tournamenttype,
		 * "Dark Web Hackers", Meta.ENDOFTIME, Meta.RELEASE2));
		 */
		List<Hitter> metaHitters = new ArrayList<Hitter>();
		for (Era era : meta.getEras()) {
			log.info(tournamenttype + "|" + era.getEnd() + "|" + era.getStart());
			Hitter hitter = stats57Repo.getMetaHitter(tournamenttype, era.getEnd(), era.getStart());
			if (hitter == null)
				continue;
			hitter.setPos(era.getName());
			metaHitters.add(hitter);
		}

		meta.setMetaHitters(metaHitters);

		meta.setRecordOverall(stats57Repo.getTopTeams(tournamenttype, Meta.ENDOFTIME, Meta.LAUNCH));
		meta.setRecordR3(stats57Repo.getTopTeams(tournamenttype, Meta.ENDOFTIME, Meta.RELEASE3));
		meta.setRecordR2(stats57Repo.getTopTeams(tournamenttype, Meta.RELEASE3, Meta.RELEASE2));
		meta.setRecordR1(stats57Repo.getTopTeams(tournamenttype, Meta.RELEASE2, Meta.RELEASE1));
		meta.setRecordLaunch(stats57Repo.getTopTeams(tournamenttype, Meta.RELEASE1, Meta.LAUNCH));

		List<Record> hackerRecords = new ArrayList<Record>();
		for (Era era : meta.getEras()) {
			Record record = stats57Repo.getRecord(tournamenttype, Meta.myTeam, era.getEnd(), era.getStart());
			if (record == null)
				continue;
			record.setEra(era);
			hackerRecords.add(record);
		}
		meta.setHackerRecords(hackerRecords);
		
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		LocalDateTime now = tomorrow.atTime(0, 0);
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/Detroit"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
		List<Record> hackerDaily = new ArrayList<Record>();
		for (int x = 0; x<21 ; x++) {
			//Record record = stats57Repo.getRecord(tournamenttype, Meta.myTeam, now.minusDays(x-1), now.minusDays(x));
			Record record = stats57Repo.getRecord(tournamenttype, Meta.myTeam, tomorrowMidnight.minusDays(x-1), tomorrowMidnight.minusDays(x));
			//log.info("Time: "+ tomorrowMidnight.minusDays(x-1) + " Time2: "+ tomorrowMidnight.minusDays(x));
			if (record == null)
				continue;
			record.setEra(new Era(tomorrowMidnight.minusDays(x-1).toString(), "time", LocalDateTime.now().minusDays(7), Meta.ENDOFTIME));
			hackerDaily.add(record);
		}
		meta.setHackerDaily(hackerDaily);
		
		model.addAttribute("meta", meta);
		return "meta";
	}

	@RequestMapping("/player/{cid}")
	public String player(Model model, @PathVariable int cid, @RequestParam(required = false) String team) {
		Player player = new Player();
		Meta meta = new Meta("TEST");
		Hitter hitter;
		for (Tournament tournament : meta.getTournies()) {
			for (Era era : meta.getEras()) {
				hitter = stats57Repo.getTeamHitter(cid, tournament.getDbName(), Meta.myTeam, era.getEnd(), era.getStart());
				if (hitter != null)
					player.addStats(tournament.getDisplayName(), Meta.myTeam, era.getName(), hitter);

				if (team != null && !team.equals(Meta.myTeam)) {
					hitter = stats57Repo.getTeamHitter(cid, tournament.getDbName(), team, era.getEnd(), era.getStart());
					if (hitter != null)
						player.addStats(tournament.getDisplayName(), team, era.getName(), hitter);
				}

				hitter = stats57Repo.getHitter(cid, tournament.getDbName(), era.getEnd(), era.getStart());
				if (hitter != null)
					player.addStats(tournament.getDisplayName(), "Overall", era.getName(), hitter);
			}
		}

		hitter = stats57Repo.getHitter(cid, "PerfectDraft", Meta.ENDOFTIME, Meta.LAUNCH);
		if (hitter != null)
			player.addStats("PerfectDraft", "Overall", "AllTime", hitter);

		model.addAttribute("player", player);
		return "player";
	}

	@RequestMapping("/player/{cid}/trend")
	public String playerTrend(Model model, @PathVariable int cid, @RequestParam(required = false) String team,
			@RequestParam(required = true) String type) {
		Player player = new Player();
		Meta meta = new Meta("TEST");
		Tournament tournie = meta.getTournamentByName(type);
		Hitter hitter;
		List<Integer> slices = new ArrayList<Integer>() {
			{
				add(1);
				add(2);
				add(3);
				add(4);
				add(5);
				add(6);
				add(7);
			}
		};
		for (Integer slice : slices) {
			LocalDateTime when = LocalDateTime.now().minusDays(slice.intValue());
			hitter = stats57Repo.getTeamHitter(cid, tournie.getDbName(), Meta.myTeam, Meta.ENDOFTIME, when);
			if (hitter != null)
				player.addStats(type, Meta.myTeam, when.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), hitter);

			if (team != null && !team.equals(Meta.myTeam)) {
				hitter = stats57Repo.getTeamHitter(cid, tournie.getDbName(), team, Meta.ENDOFTIME, when);
				if (hitter != null)
					player.addStats(type, team, when.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), hitter);
			}

		}
		model.addAttribute("player", player);
		return "player";
	}

	@RequestMapping("/player/{cid}/tbt")
	public String playerTbt(Model model, @PathVariable int cid, @RequestParam(required = false) String time,
			@RequestParam(required = true) String type) {
		Player player = new Player();
		Meta meta = new Meta(type);
		Tournament tournie = meta.getTournamentByName(type);
		Era era = meta.getEraByName(time);
		
		Predicate<Hitter> byIp50 = hitter -> hitter.getInnings().intValue() > 50;
		Predicate<Hitter> byIp100 = hitter -> hitter.getInnings().intValue() > 100;
		Predicate<Hitter> byIp200 = hitter -> hitter.getInnings().intValue() > 200;
		Predicate<Hitter> byIp300 = hitter -> hitter.getInnings().intValue() > 300;
		Predicate<Hitter> byIp400 = hitter -> hitter.getInnings().intValue() > 400;
		
		List<Hitter> hitters = stats57Repo.getHitterByTeamList( tournie.getDbName(), cid, era.getEnd(), era.getStart());
		hitters = hitters.stream().filter(byIp50).collect(Collectors.toList());
		hitters.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getEra)));
		for(Hitter hitter : hitters) {
			player.addStats(tournie.getDisplayName(), hitter.getCard(), era.getName(), hitter);

		}
		
		player.setCard(stats57Repo.getCard(cid));
		model.addAttribute("player", player);
		return "player";
	}
}