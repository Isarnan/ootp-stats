package com.felarca.ootp.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
public class Meta {
	// public final static Date ENDOFTIME =
	// DatatypeConverter.parseDateTime("2041-05-31T10:00:00-05:00").getTime();
	public final static LocalDateTime ENDOFTIME = LocalDateTime.of(2035, Month.JULY, 29, 19, 30, 40);
	public final static LocalDateTime LAUNCH = LocalDateTime.of(2022, Month.MAY, 3, 19, 30, 40);
	public final static LocalDateTime RELEASE1 = LocalDateTime.of(2022, Month.JUNE, 6, 10, 10, 10);
	public final static LocalDateTime RELEASE2 = LocalDateTime.of(2022, Month.JULY, 31, 10, 30, 40);
	public final static LocalDateTime RELEASE3 = LocalDateTime.of(2022, Month.AUGUST, 28, 12, 30, 40);

	// public final static Date LAUNCH =
	// DatatypeConverter.parseDateTime("1776-06-04T00:00:00-05:00").getTime();
	// public final static Date RELEASE1 =
	// DatatypeConverter.parseDateTime("1776-06-04T00:00:00-05:00").getTime();
	// public final static Date RELEASE2 =
	// DatatypeConverter.parseDateTime("2021-05-31T10:00:00-05:00").getTime();

	public final static String myTeam = "Dark Web Hackers";
	public final static String defaultEra = "R1";
	public final static String defaultTournament = "gold";

	@Getter
	@Setter
	private String tournamentType;

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

		this.tournies.add(new Tournament("Iron", "Iron16", "14Day"));
		this.tournies.add(new Tournament("Bronze", "Bronze32", "14Day"));
		this.tournies.add(new Tournament("Gold", "Gold32", "14Day"));
		this.tournies.add(new Tournament("PerfectTeam", "PerfectTeam", "Alltime"));
		this.tournies.add(new Tournament("PerfectDraft", "PerfectDraft", "Alltime"));
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

	public String getTier() {
		if (this.tournamentType.equals("Gold32"))
			return "gold";
		else if (this.tournamentType.equals("PerfectDraft"))
			return "perfectdraft";
		else if (this.tournamentType.equals("Bronze32"))
			return "bronze";
		else if (this.tournamentType.equals("Iron16"))
			return "iron";
		else
			return "bronze";
	}

	public Era getEraByName(String name) {
		for (Era temp : this.eras) {
			log.info("name: " + name + " temp: " + temp.getName());
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
}
