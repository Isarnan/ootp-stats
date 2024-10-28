package com.felarca.ootp.domain;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.felarca.ootp.domain.dao.StatYear;
import com.felarca.ootp.domain.results.CardTournamentResult;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class Meta {
	// public final static Date ENDOFTIME =
	// DatatypeConverter.parseDateTime("2041-05-31T10:00:00-05:00").getTime();
	public final static LocalDateTime ENDOFTIME = LocalDateTime.of(2035, Month.JULY, 29, 19, 30, 40);
	public final static LocalDateTime LAUNCH = LocalDateTime.of(2024, Month.MARCH, 15, 0, 30, 40);
	public final static LocalDateTime RELEASE1 = LocalDateTime.of(2024, Month.APRIL, 1, 10, 10, 10);
	public final static LocalDateTime RELEASE2 = LocalDateTime.of(2024, Month.MAY, 6, 10, 10, 10);
	public final static LocalDateTime RELEASE3 = LocalDateTime.of(2024, Month.JUNE, 3, 10, 30, 40);
	public final static LocalDateTime RELEASE4 = LocalDateTime.of(2024, Month.JULY, 1, 12, 30, 40);

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

	public static List<StatYear> statYears;

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
	private List<Release> eras = new ArrayList<Release>();

	@Setter
	@Getter
	private List<OotpModel> tournies = new ArrayList<OotpModel>();

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

		this.eras.add(new Release("7Day", "time", LocalDateTime.now().minusDays(7), Meta.ENDOFTIME));
		this.eras.add(new Release("14Day", "time", LocalDateTime.now().minusDays(14), Meta.ENDOFTIME));
		this.eras.add(new Release("30Day", "time", LocalDateTime.now().minusDays(30), Meta.ENDOFTIME));
		this.eras.add(new Release("R3", "content", Meta.RELEASE3, Meta.ENDOFTIME));
		this.eras.add(new Release("R2", "content", Meta.RELEASE2, Meta.RELEASE3));
		this.eras.add(new Release("R1", "content", Meta.RELEASE1, Meta.RELEASE2));
		this.eras.add(new Release("Launch", "content", Meta.LAUNCH, Meta.RELEASE1));
		this.eras.add(new Release("AllTime", "time", Meta.LAUNCH, Meta.ENDOFTIME));

		this.tournies.add(new OotpModel("Iron", "Iron16", "14Day","iron"));
		this.tournies.add(new OotpModel("Bronze", "Bronze16", "14Day","bronze"));
		this.tournies.add(new OotpModel("Gold", "Gold32", "14Day", "gold"));
		this.tournies.add(new OotpModel("PerfectTeam", "PerfectTeam", "Alltime","perfectteam"));
		this.tournies.add(new OotpModel("PerfectDraft", "PerfectDraft", "Alltime","perfectdraft"));
		this.tournies.add(new OotpModel("Daily Live Gold", "DailyLiveGold", "Alltime", "dailylivegold"));
		this.tournies.add(new OotpModel("Daily Bronze Floor Cap", "DailyBronzeFloorCap", "Alltime", "dailybronzefloorcap"));
		this.tournies.add(new OotpModel("Open Tournaments", "Open", "Alltime", "open"));

		this.tournies.add(new OotpModel("Perfecto", "Perfecto", "Alltime", "perfecto"));
		this.tournies.add(new OotpModel("Live Open", "LiveOpen", "Alltime", "liveopen"));
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
	public Release getEraByName(String name) {
		for (Release temp : this.eras) {
			//log.info("name: " + name + " temp: " + temp.getName());
			if (temp.getName().equals(name) || temp.getName().toLowerCase().equals(name.toLowerCase())) {
				return temp;
			}
		}
		return null;
	}

	public OotpModel getTournamentByName(String name) {
		log.finest("tournies: " + this.tournies.size());
		for (OotpModel temp : this.tournies) {
			log.finest("name: " + name + " temp: " + temp.getDisplayName());
			if (temp.getDisplayName().equals(name) || temp.getDisplayName().toLowerCase().equals(name)) {
				return temp;
			}
		}
		return null;
	}

	public String getDBTypeByURL(String urlSegment) {
		for (OotpModel temp : this.tournies) {
			if (temp.getUrlSegment().equals(urlSegment)) {
				return temp.getDbName();
			}
		}
		return null;
	}
	public Release getDefaultTime() {
		String urlSegment = this.tournamentType;
		for (OotpModel temp : this.tournies) {
			if (temp.getUrlSegment().equals(urlSegment)) {
				return temp.getDefaultRelease();
			}
		}
		return null;
	}

	static public StatYear getStatYear(String year){
		StatYear sy = new StatYear();
		for( StatYear x : Meta.statYears){
			if( x.getYear().equals(year)){
				sy = x;
				break;
			}
		}
		return sy;
	}
}
