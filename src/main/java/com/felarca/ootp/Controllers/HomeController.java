package com.felarca.ootp.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.Repositories.GoldHackerHittingRepository;
import com.felarca.ootp.Repositories.GoldHackerPitchingRepository;
import com.felarca.ootp.Repositories.GoldHittingRepository;
import com.felarca.ootp.Repositories.GoldPitchingRepository;
import com.felarca.ootp.Repositories.HackerHittingRepository;
import com.felarca.ootp.Repositories.HackerPitchingRepository;
import com.felarca.ootp.Repositories.HittingRepository;
import com.felarca.ootp.Repositories.PitchingRepository;
import com.felarca.ootp.Repositories.SkipRepository;
import com.felarca.ootp.Repositories.Stats2Repository;
import com.felarca.ootp.Repositories.Stats57Repository;
import com.felarca.ootp.Repositories.Stats72Repository;
import com.felarca.ootp.domain.Card;
import com.felarca.ootp.domain.CardStatSet;
import com.felarca.ootp.domain.Era;
import com.felarca.ootp.domain.EraSet;
import com.felarca.ootp.domain.Hitter;
import com.felarca.ootp.domain.Meta;
import com.felarca.ootp.domain.MetaCard;
import com.felarca.ootp.domain.Player;
import com.felarca.ootp.domain.Record;
import com.felarca.ootp.domain.Skip;
import com.felarca.ootp.domain.Tournament;
import com.felarca.ootp.domain.TournamentSet;
import com.felarca.ootp.domain.results.CardTournamentResult;

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
	Stats72Repository stats72Repo;
	@Autowired
	Stats2Repository stats2Repo;
	@Autowired
	CardsRepository cardsRepo;
	@Autowired
	EraSet eraSet;
	@Autowired
	TournamentSet tournamentSet;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("skips", skipRepo.findAll());
		for (Skip skip : skipRepo.findAll()) {
			log.finest("Skip index: " + skip.getId());
		}
		return "index";
	}

	@RequestMapping("/team/{tournamenttype}/pitchers")
	public String hackerPitchers(Model model, @PathVariable String tournamenttype,
			@RequestParam(required = false) String strIp,
			@RequestParam(required = false) String time, @RequestParam(required = false) String team) {
		Meta meta = new Meta(tournamenttype);
		Tournament tournie = meta.getTournamentByName(tournamenttype);
		Era era = meta.getEraByName(time);
		log.info("Era: " + era.getName() + " Tournament: " + tournamenttype + " DbName: " + tournie.getDbName()
				+ " Start: " + era.getStart() + " end: " + era.getEnd());

		// if (time == null)
		// time = tournie.getDefaultEra();

		List<Hitter> list = stats57Repo.getTeamHittersList(tournie.getDbName(), team, era.getEnd(), era.getStart());

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
		model.addAttribute("owned", stats2Repo);
		model.addAttribute("cards", cardsRepo);
		if (tournie.getDisplayName().equals("PerfectDraft")) {
			return "draftpitchers";
		} else {
			return "pitchers";
		}
		// return "pitchers";
	}

	@RequestMapping("/team/{tournamenttype}/hitters")
	public String hackerHitters(Model model, @PathVariable String tournamenttype,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String filter, @RequestParam(required = false) String time,
			@RequestParam(required = false) String team) {
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

		List<CardTournamentResult> list;
		CardTournamentResult metaHitter;

		if (time != null && time.equals("R2")) {
			list = stats72Repo.getTeamResultList(tournamenttype, team, Meta.RELEASE3, Meta.RELEASE2);
			metaHitter = stats72Repo.getTeamMetaResult(tournamenttype, team, Meta.RELEASE3, Meta.RELEASE2);
		} else if (time.equals("R1")) {
			list = stats72Repo.getTeamResultList(tournamenttype, team, Meta.RELEASE2, Meta.RELEASE1);
			metaHitter = stats72Repo.getTeamMetaResult(tournamenttype, team, Meta.RELEASE2, Meta.RELEASE1);
		} else if (time.equals("R3")) {
			list = stats72Repo.getTeamResultList(tournamenttype, team, Meta.ENDOFTIME, Meta.RELEASE3);
			metaHitter = stats72Repo.getTeamMetaResult(tournamenttype, team, Meta.ENDOFTIME, Meta.RELEASE3);
		} else if (time.equals("7Day")) {
			list = stats72Repo.getTeamResultList(tournamenttype, team, Meta.ENDOFTIME,
					LocalDateTime.now().minusDays(7));
			metaHitter = stats72Repo.getTeamMetaResult(tournamenttype, team, Meta.ENDOFTIME,
					LocalDateTime.now().minusDays(7));
		} else if (time.equals("14Day")) {
			list = stats72Repo.getTeamResultList(tournamenttype, team, Meta.ENDOFTIME,
					LocalDateTime.now().minusDays(14));
			metaHitter = stats72Repo.getTeamMetaResult(tournamenttype, team, Meta.ENDOFTIME,
					LocalDateTime.now().minusDays(14));
		} else if (time.equals("30Day")) {
			list = stats72Repo.getTeamResultList(tournamenttype, team, Meta.ENDOFTIME,
					LocalDateTime.now().minusDays(30));
			metaHitter = stats72Repo.getTeamMetaResult(tournamenttype, team, Meta.ENDOFTIME,
					LocalDateTime.now().minusDays(30));
		} else {
			list = stats72Repo.getTeamResultList(tournamenttype, team, Meta.ENDOFTIME, Meta.LAUNCH);
			metaHitter = stats72Repo.getTeamMetaResult(tournamenttype, team, Meta.ENDOFTIME, Meta.LAUNCH);
			log.info(team + tournamenttype + Meta.ENDOFTIME + Meta.LAUNCH);
			// List<Hitter> list = stats57Repo.getHackerHittersList(tournamenttype);
		}
		Predicate<CardTournamentResult> byPa = hitter -> hitter.getPa().intValue() > 3;
		Predicate<CardTournamentResult> byPipa = hitter -> hitter.getPipa() > 4.2;
		if (filter != null && filter.equals("pipa"))
			list = list.stream().filter(byPipa).collect(Collectors.toList());
		else
			list = list.stream().filter(byPa).collect(Collectors.toList());

		/** Sorting **/
		if ((sort == null && (tournamenttype.equals("Gold32") || tournamenttype.equals("Bronze16")
				|| tournamenttype.equals("PerfectTeam") || tournamenttype.equals("PerfectDraft")))
				|| (sort != null && sort.equals("ops")))
			list.sort(Comparator.nullsFirst(Comparator.comparing(CardTournamentResult::getOps).reversed()));
		else if ((sort == null && (tournamenttype.equals("Iron16"))) || (sort != null && sort.equals("obp")))
			list.sort(Comparator.nullsFirst(Comparator.comparing(CardTournamentResult::getObp).reversed()));
		else if (sort != null && sort.equals("pa"))
			list.sort(Comparator.nullsFirst(Comparator.comparing(CardTournamentResult::getPa).reversed()));
		log.info("Card: " + metaHitter.getCard());

		model.addAttribute("metaHitter", metaHitter);
		model.addAttribute("hitters", list);
		model.addAttribute("owned", stats2Repo);
		model.addAttribute("cards", cardsRepo);
		if (tournamenttype.equals("PerfectDraft")) {
			return "drafthitters";
		} else {
			return "hitters";
		}
	}

	@RequestMapping("/{tournamenttype}/hitters")
	public String bronzeHitter(Model model, @PathVariable String tournamenttype,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String filter, @RequestParam(required = false) Integer pa,
			@RequestParam(required = false) String strPipa, @RequestParam(required = false) String time) {

		Meta meta = new Meta(tournamenttype);
		String dbType = meta.getDBTypeByURL(tournamenttype);

		if (time == null && (dbType.equals("Gold32") || dbType.equals("Bronze16") || dbType.equals("Iron16")
				|| dbType.equals("DailyLiveGold")))
			time = "LAUNCH";
		else if (time == null && (dbType.equals("PerfectTeam") || dbType.equals("PerfectDraft")))
			time = "alltime";
		else
			time = "alltime";

		List<CardTournamentResult> list;

		if (time != null && time.equals("R2"))
			list = stats72Repo.getResultList(dbType, Meta.ENDOFTIME, Meta.RELEASE2);
		else if (time.equals("R1"))
			list = stats72Repo.getResultList(dbType, Meta.RELEASE2, Meta.RELEASE1);
		else if (time.equals("14Day"))
			list = stats72Repo.getResultList(dbType, Meta.ENDOFTIME, LocalDateTime.now().minusDays(14));
		else if (time.equals("30Day"))
			list = stats72Repo.getResultList(dbType, Meta.ENDOFTIME, LocalDateTime.now().minusDays(30));
		else
			list = stats72Repo.getResultList(dbType, Meta.ENDOFTIME, Meta.LAUNCH);

		if (pa != null) {
			Predicate<CardTournamentResult> byPa = hitter -> hitter.getPa().intValue() > pa.intValue();
			list = list.stream().filter(byPa).collect(Collectors.toList());
		} else {
			Predicate<CardTournamentResult> byPa = hitter -> hitter.getPa().intValue() > 100;
			list = list.stream().filter(byPa).collect(Collectors.toList());
		}

		if (sort == null || sort.equals("ops"))
			list.sort(Comparator.nullsFirst(Comparator.comparing(CardTournamentResult::getOps).reversed()));
		else
			list.sort(Comparator.nullsFirst(Comparator.comparing(CardTournamentResult::getObp).reversed()));

		model.addAttribute("meta", meta);
		model.addAttribute("hitters", list);
		model.addAttribute("owned", stats2Repo);
		model.addAttribute("cards", cardsRepo);
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
		Tournament t = tournamentSet.getTournamentByUrlSegment(tournamenttype);
		Era era;
		if (time == null) {
 			era = t.getDefaultEra();
		} else {
			era = meta.getEraByName(time);
		}
		
		String dbType = meta.getDBTypeByURL(tournamenttype);


		List<CardTournamentResult> list = stats72Repo.getResultList(dbType, era.getEnd(), era.getStart());;

		if(ip != null){			
			Predicate<CardTournamentResult> byIp = hitter -> hitter.getInnings().intValue() > ip.intValue();
			list = list.stream().filter(byIp).collect(Collectors.toList());
		} else {
			Predicate<CardTournamentResult> byIp = hitter -> hitter.getInnings().intValue() > 30;
			list = list.stream().filter(byIp).collect(Collectors.toList());
		}
		if(pig != null){			
			Predicate<CardTournamentResult> byPig = hitter -> Double.valueOf(hitter.getPig()) > pig.intValue();
			list = list.stream().filter(byPig).collect(Collectors.toList());
		} else {
			Predicate<CardTournamentResult> byPig = hitter -> Double.valueOf(hitter.getPig()) > 1;
			list = list.stream().filter(byPig).collect(Collectors.toList());
		}
		
		// Sorting
		list.sort(Comparator.nullsFirst(Comparator.comparing(CardTournamentResult::getEra)));
		// list.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getInnings)));
		model.addAttribute("meta", meta);
		model.addAttribute("pitchers", list);
		model.addAttribute("owned", stats2Repo);
		model.addAttribute("cards", cardsRepo);
		if (dbType.equals("PerfectDraft")) {
			return "draftpitchers";
		} else {
			return "pitchers";
		}
	}

	@RequestMapping("/{tournamenttype}/meta")
	public String universalMeta(Model model, @PathVariable String tournamenttype) {
		Meta meta = new Meta(tournamenttype);
		String dbType = meta.getDBTypeByURL(tournamenttype);

		List<CardTournamentResult> metaResults = new ArrayList<CardTournamentResult>();
		for (Era era : meta.getEras()) {
			log.info(tournamenttype + "|" + era.getEnd() + "|" + era.getStart());
			CardTournamentResult result = stats72Repo.getMetaResult(dbType, era.getEnd(), era.getStart());
			if (result == null)
				continue;
			result.setPos(era.getName());
			metaResults.add(result);
		}

		meta.setMetaResults(metaResults);

		meta.setRecordOverall(stats72Repo.getTopTeams(dbType, Meta.ENDOFTIME, Meta.LAUNCH));
		meta.setRecordR3(stats72Repo.getTopTeams(dbType, Meta.ENDOFTIME, Meta.RELEASE3));
		meta.setRecordR2(stats72Repo.getTopTeams(dbType, Meta.RELEASE3, Meta.RELEASE2));
		meta.setRecordR1(stats72Repo.getTopTeams(dbType, Meta.RELEASE2, Meta.RELEASE1));
		meta.setRecordLaunch(stats72Repo.getTopTeams(dbType, Meta.RELEASE1, Meta.LAUNCH));

		List<Record> hackerRecords = new ArrayList<Record>();
		for (Era era : meta.getEras()) {
			Record record = stats72Repo.getRecord(dbType, Meta.myTeam, era.getEnd(), era.getStart());
			if (record == null)
				continue;
			record.setEra(era);
			hackerRecords.add(record);
		}
		meta.setHackerRecords(hackerRecords);

		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/Detroit"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
		List<Record> hackerDaily = new ArrayList<Record>();
		for (int x = 0; x < 21; x++) {
			// Record record = stats57Repo.getRecord(tournamenttype, Meta.myTeam,
			// now.minusDays(x-1), now.minusDays(x));
			Record record = stats72Repo.getRecord(dbType, Meta.myTeam, tomorrowMidnight.minusDays(x - 1),
					tomorrowMidnight.minusDays(x));
			// log.info("Time: "+ tomorrowMidnight.minusDays(x-1) + " Time2: "+
			// tomorrowMidnight.minusDays(x));
			if (record == null)
				continue;
			record.setEra(new Era(tomorrowMidnight.minusDays(x - 1).toString(), "time",
					LocalDateTime.now().minusDays(7), Meta.ENDOFTIME));
			hackerDaily.add(record);
		}
		meta.setHackerDaily(hackerDaily);

		MetaCard metaCard = new MetaCard();
		for (Era era : meta.getEras()) {
			List<CardTournamentResult> results = stats72Repo.getResultList(dbType, era.getEnd(), era.getStart());
			double inningsCounter = 0;
			double controlvlCounter = 0, controlvrCounter = 0;
			double stuffvlCounter = 0, stuffvrCounter = 0;
			double phrvlCounter = 0, phrvrCounter = 0;
			double pbabipvlCounter = 0, pbabipvrCounter = 0;

			double paCounter = 0;
			double eyevlCounter = 0, eyevrCounter = 0;
			double avkvlCounter = 0, avkvrCounter = 0;
			double powervlCounter = 0, powervrCounter = 0;
			double babipvlCounter = 0, babipvrCounter = 0;

			for (CardTournamentResult result : results) {
				Card card = cardsRepo.getCard(result.getCid());
				double ip = result.getInnings();
				double pa = result.getPa().doubleValue();
				if (ip > 0) {
					inningsCounter = inningsCounter + ip;
					controlvlCounter = controlvlCounter + ip * card.getControlvl();
					controlvrCounter = controlvrCounter + ip * card.getControlvr();
					stuffvlCounter = stuffvlCounter + ip * card.getStuffvl();
					stuffvrCounter = stuffvrCounter + ip * card.getStuffvr();
					phrvlCounter = phrvlCounter + ip * card.getPhrvl();
					phrvrCounter = phrvrCounter + ip * card.getPhrvr();
					pbabipvlCounter = pbabipvlCounter + ip * card.getPbabipvl();
					pbabipvrCounter = pbabipvrCounter + ip * card.getPbabipvr();
				}
				if (pa > 0) {
					paCounter = paCounter + pa;
					eyevlCounter = eyevlCounter + pa * card.getEyevL();
					eyevrCounter = eyevrCounter + pa * card.getEyevR();
					avkvlCounter = avkvlCounter + pa * card.getKsvL();
					avkvrCounter = avkvrCounter + pa * card.getKsvR();
					powervlCounter = powervlCounter + pa * card.getPowervL();
					powervrCounter = powervrCounter + pa * card.getPowervR();
					babipvlCounter = babipvlCounter + pa * card.getBABIPvL();
					babipvrCounter = babipvrCounter + pa * card.getBABIPvR();
				}
			}
			CardStatSet averagesvr = new CardStatSet(), averagesvl = new CardStatSet();
			averagesvl.setControl(controlvlCounter / inningsCounter);
			averagesvl.setPbabip(pbabipvlCounter / inningsCounter);
			averagesvl.setPhr(phrvlCounter / inningsCounter);
			averagesvl.setStuff(stuffvlCounter / inningsCounter);
			averagesvl.setEye(eyevlCounter / paCounter);
			averagesvl.setBabip(babipvlCounter / paCounter);
			averagesvl.setPower(powervlCounter / paCounter);
			averagesvl.setAvk(avkvlCounter / paCounter);
			metaCard.setAvgVsLeft(averagesvl);

			averagesvr.setPhr(phrvrCounter / inningsCounter);
			averagesvr.setControl(controlvrCounter / inningsCounter);
			averagesvr.setPbabip(pbabipvrCounter / inningsCounter);
			averagesvr.setStuff(stuffvrCounter / inningsCounter);
			averagesvr.setEye(eyevrCounter / paCounter);
			averagesvr.setBabip(babipvrCounter / paCounter);
			averagesvr.setPower(powervrCounter / paCounter);
			averagesvr.setAvk(avkvrCounter / paCounter);
			metaCard.setAvgVsRight(averagesvr);

			// log.info("IP: " + inningsCounter + "Control vl:" + controlCounter /
			// inningsCounter);
		}

		model.addAttribute("metacard", metaCard);
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
				hitter = stats57Repo.getTeamHitter(cid, tournament.getDbName(), Meta.myTeam, era.getEnd(),
						era.getStart());
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

		Object[][] startData = { { 1, 2 }, { 3, 4 }, { 7, 8 } };

		Hitter starter = stats57Repo.getStarter(cid, "Bronze16", Meta.ENDOFTIME, Meta.LAUNCH);
		player.addStats("Bronze16", "Starter", "AllTime", starter);
		// log.info("Started: " + starter.getP_gamesstarted());

		Hitter reliever = stats57Repo.getReliever(cid, "Bronze16", Meta.ENDOFTIME, Meta.LAUNCH);
		player.addStats("Bronze16", "Reliever", "AllTime", reliever);
		// log.info("Started: " + reliever.getP_gamesstarted());

		player.setPitchingSplits(cardsRepo.pitcherSplits(cid));
		Card card = cardsRepo.getCard(cid);

		model.addAttribute("card", card);
		model.addAttribute("startData", startData);
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
		/*
		 * List<Integer> slices = new ArrayList<Integer>() {
		 * {
		 * add(1);
		 * add(2);
		 * add(3);
		 * add(4);
		 * add(5);
		 * add(6);
		 * add(7);
		 * }
		 * };
		 * for (Integer slice : slices) {
		 * LocalDateTime when = LocalDateTime.now().minusDays(slice.intValue());
		 * hitter = stats57Repo.getTeamHitter(cid, tournie.getDbName(), Meta.myTeam,
		 * Meta.ENDOFTIME, when);
		 * if (hitter != null)
		 * player.addStats(type, Meta.myTeam,
		 * when.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), hitter);
		 * 
		 * if (team != null && !team.equals(Meta.myTeam)) {
		 * hitter = stats57Repo.getTeamHitter(cid, tournie.getDbName(), team,
		 * Meta.ENDOFTIME, when);
		 * if (hitter != null)
		 * player.addStats(type, team,
		 * when.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), hitter);
		 * }
		 * 
		 * }
		 */
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/Detroit"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);

		for (int i = 0; i < 20; i++) {

			LocalDateTime end = todayMidnight.minusDays(i);
			LocalDateTime start = todayMidnight.minusDays(i - 1);
			log.info("tournie: " + tournie.getDbName() + " : " + start.toString() + " : " + end.toString());
			hitter = stats57Repo.getTeamHitter(cid, tournie.getDbName(), Meta.myTeam, start, end);
			log.info("HItter: " + hitter);
			if (hitter != null)
				player.addStats(type, Meta.myTeam, end.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), hitter);

			if (team != null && !team.equals(Meta.myTeam)) {
				hitter = stats57Repo.getTeamHitter(cid, tournie.getDbName(), team, Meta.ENDOFTIME, start);
				if (hitter != null)
					player.addStats(type, team, end.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), hitter);
			}
		}

		Card card = cardsRepo.getCard(cid);

		model.addAttribute("card", card);
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

		List<Hitter> hitters = stats57Repo.getHitterByTeamList(tournie.getDbName(), cid, era.getEnd(), era.getStart());
		hitters = hitters.stream().filter(byIp50).collect(Collectors.toList());
		hitters.sort(Comparator.nullsFirst(Comparator.comparing(Hitter::getEra)));
		for (Hitter hitter : hitters) {
			player.addStats(tournie.getDisplayName(), hitter.getCard(), era.getName(), hitter);

		}

		player.setCard(stats57Repo.getCard(cid));
		Card card = cardsRepo.getCard(cid);

		model.addAttribute("card", card);
		model.addAttribute("player", player);
		return "player";

	}
}