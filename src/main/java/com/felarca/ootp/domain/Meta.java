package com.felarca.ootp.domain;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.felarca.ootp.domain.results.CardTournamentResult;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class Meta {
	// public final static Date ENDOFTIME =
	// DatatypeConverter.parseDateTime("2041-05-31T10:00:00-05:00").getTime();
	public final static LocalDateTime ENDOFTIME = LocalDateTime.of(2035, Month.JULY, 29, 19, 30, 40);
	public final static LocalDateTime LAUNCH = LocalDateTime.of(2023, Month.MARCH, 24, 0, 30, 40);
	public final static LocalDateTime RELEASE1 = LocalDateTime.of(2023, Month.MAY, 1, 10, 10, 10);
	public final static LocalDateTime RELEASE2 = LocalDateTime.of(2023, Month.JULY, 31, 10, 30, 40);
	public final static LocalDateTime RELEASE3 = LocalDateTime.of(2023, Month.AUGUST, 28, 12, 30, 40);

	// public final static Date LAUNCH =
	// DatatypeConverter.parseDateTime("1776-06-04T00:00:00-05:00").getTime();
	// public final static Date RELEASE1 =
	// DatatypeConverter.parseDateTime("1776-06-04T00:00:00-05:00").getTime();
	// public final static Date RELEASE2 =
	// DatatypeConverter.parseDateTime("2021-05-31T10:00:00-05:00").getTime();

	public final static String myTeam = "Dark Web Hackers";
	public final static String defaultEra = "Launch";
	public final static String defaultTournament = "gold";
	public final static String[] CardTier = {"Iron", "Bronze", "Silver", "Gold", "Diamond", "Perfect"};
	
	public final static int BRONZE_IFR_GREATNESS = 90;
	public final static int BRONZE_IFR_GOODNESS = 70;
	public final static int BRONZE_IFR_FINE = 50;


	@Getter
	@Setter
	private String tournamentType;

	@Getter
	@Setter
	private String round;

	@Getter
	@Setter
	private Record hackerRecordOverall;

	@Getter
	@Setter
	private Record hackerRecord14Day;

	@Getter
	@Setter
	private Record hackerRecord30Day;

	@Getter
	@Setter
	private Record hackerRecordR1;

	@Getter
	@Setter
	private Record hackerRecordR2;

	@Setter
	@Getter
	private List<Era> eras = new ArrayList<Era>();

	@Setter
	@Getter
	private List<Tournament> tournies = new ArrayList<Tournament>();

	@Getter
	@Setter
	private List<Record> hackerRecords;
	
	@Getter
	@Setter
	private List<Record> hackerDaily;

	@Getter
	@Setter
	private List<Hitter> metaHitters;

	@Getter
	@Setter
	private List<CardTournamentResult> metaResults;

	public Meta(String tournamentType) {
		super();
		this.tournamentType = tournamentType;

		this.eras.add(new Era("7Day", "time", LocalDateTime.now().minusDays(7), Meta.ENDOFTIME));
		this.eras.add(new Era("14Day", "time", LocalDateTime.now().minusDays(14), Meta.ENDOFTIME));
		this.eras.add(new Era("30Day", "time", LocalDateTime.now().minusDays(30), Meta.ENDOFTIME));
		this.eras.add(new Era("R3", "content", Meta.RELEASE3, Meta.ENDOFTIME));
		this.eras.add(new Era("R2", "content", Meta.RELEASE2, Meta.RELEASE3));
		this.eras.add(new Era("R1", "content", Meta.RELEASE1, Meta.RELEASE2));
		this.eras.add(new Era("Launch", "content", Meta.LAUNCH, Meta.RELEASE1));
		this.eras.add(new Era("AllTime", "time", Meta.LAUNCH, Meta.ENDOFTIME));

		this.tournies.add(new Tournament("Iron", "Iron16", "14Day","iron"));
		this.tournies.add(new Tournament("Bronze", "Bronze16", "14Day","bronze"));
		this.tournies.add(new Tournament("Gold", "Gold32", "14Day", "gold"));
		this.tournies.add(new Tournament("PerfectTeam", "PerfectTeam", "Alltime","perfectteam"));
		this.tournies.add(new Tournament("PerfectDraft", "PerfectDraft", "Alltime","perfectdraft"));
		this.tournies.add(new Tournament("Daily Live", "DailyLive", "Alltime", "dailylive"));
		this.tournies.add(new Tournament("Daily Live Gold", "DailyLiveGold", "Alltime", "dailylivegold"));
		this.tournies.add(new Tournament("Daily Bronze Floor Cap", "DailyBronzeFloorCap", "Alltime", "dailybronzefloorcap"));

	}

	@Getter
	@Setter
	private List<Record> recordOverall;

	public List<Record> getRecordOverall(Integer filter) {
		List<Record> returnList = new ArrayList<Record>();
		;
		for (Record record : recordOverall) {
			if (record.getWins().intValue() > filter)
				returnList.add(record);
		}
		return returnList;
	}

	public boolean getDisplayR3() {
		return false;
	}
	public boolean getDisplayR2() {
		return false;
	}
	public boolean getDisplayR1() {
		return true;
	}
	public boolean getDisplayLaunch() {
		return false;
	}


	@Getter
	@Setter
	private List<Record> recordR3;

	public List<Record> getRecordR3(Integer filter) {
		List<Record> returnList = new ArrayList<Record>();
		;
		for (Record record : recordR3) {
			if (record.getWins().intValue() > filter)
				returnList.add(record);
		}
		return returnList;
	}

	@Getter
	@Setter
	private List<Record> recordR2;

	public List<Record> getRecordR2(Integer filter) {
		List<Record> returnList = new ArrayList<Record>();
		;
		for (Record record : recordR2) {
			if (record.getWins().intValue() > filter)
				returnList.add(record);
		}
		return returnList;
	}

	@Getter
	@Setter
	private List<Record> recordR1;

	public List<Record> getRecordR1(Integer filter) {
		List<Record> returnList = new ArrayList<Record>();
		;
		for (Record record : recordR1) {
			if (record.getWins().intValue() > filter)
				returnList.add(record);
		}
		return returnList;
	}

	@Getter
	@Setter
	private List<Record> recordLaunch;

	public List<Record> getRecordLaunch(Integer filter) {
		List<Record> returnList = new ArrayList<Record>();
		;
		for (Record record : recordLaunch) {
			if (record.getWins().intValue() > filter)
				returnList.add(record);
		}
		return returnList;
	}
	public String getUrlSegment(){
		return this.tournamentType;
	}
	public Era getEraByName(String name) {
		for (Era temp : this.eras) {
			//log.info("name: " + name + " temp: " + temp.getName());
			if (temp.getName().equals(name) || temp.getName().toLowerCase().equals(name.toLowerCase())) {
				return temp;
			}
		}
		return null;
	}

	public Tournament getTournamentByName(String name) {
		log.finest("tournies: " + this.tournies.size());
		for (Tournament temp : this.tournies) {
			log.finest("name: " + name + " temp: " + temp.getDisplayName());
			if (temp.getDisplayName().equals(name) || temp.getDisplayName().toLowerCase().equals(name)) {
				return temp;
			}
		}
		return null;
	}

	public String getDBTypeByURL(String urlSegment) {
		for (Tournament temp : this.tournies) {
			if (temp.getUrlSegment().equals(urlSegment)) {
				return temp.getDbName();
			}
		}
		return null;
	}
	public Era getDefaultTime() {
		String urlSegment = this.tournamentType;
		for (Tournament temp : this.tournies) {
			if (temp.getUrlSegment().equals(urlSegment)) {
				return temp.getDefaultEra();
			}
		}
		return null;
	}
}
