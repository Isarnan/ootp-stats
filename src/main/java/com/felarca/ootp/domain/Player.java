package com.felarca.ootp.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
@Log
public class Player {
	@Setter
	@Getter
	private int cid;
	
	@Setter
	@Getter
	private String card;
	
	@Setter
	@Getter
	private Hitter bronzeHitting;
	
	@Setter
	@Getter
	private Hitter bronzePitching;

	@Setter
	@Getter
	private Hitter bronzeHackerHitting;
	
	@Setter
	@Getter
	private Hitter bronzeHackerPitching;
	
	@Setter
	@Getter
	private Hitter goldHitting;
	
	@Setter
	@Getter
	private Hitter goldPitching;

	@Setter
	@Getter
	private Hitter goldHackerHitting;
	
	@Setter
	@Getter
	private Hitter goldHackerPitching;
	
	@Setter
	@Getter
	private Integer pitchingSplits;

	@Setter
	@Getter
	private List<StatsLine> stats = new ArrayList<StatsLine>();
	
	@Getter	
	private Hitter perfectDraft;	
	public void setPerfectDraft(Hitter hitter) {
		if(hitter == null)return;
		//hitter.cleanPos();
		this.perfectDraft = hitter;
		log.finest("PD:" + hitter.getCid());
	}
	
	@Getter	
	private Hitter perfectTeam;
	public void setPerfectTeam(Hitter hitter) {
		if(hitter == null)return;
		//hitter.cleanPos();
		this.perfectTeam = hitter;
		log.finest("PT:" + hitter.getCid() + ", PA: " + hitter.getPa() + ", IP: " + hitter.getIp());
	}
	
	@Deprecated
	public void addStats(String type, String team, String time, Hitter stats) {
		StatsLine line = new StatsLine(type, team, time, stats);
		log.info(line.toString());
		this.stats.add(line);		
	}
	
}
