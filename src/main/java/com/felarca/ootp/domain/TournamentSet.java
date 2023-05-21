package com.felarca.ootp.domain;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.Repositories.Stats72Repository;
import com.felarca.ootp.domain.results.CardTournamentResult;

import lombok.extern.java.Log;

@Log
@Component
public class TournamentSet {
    @Autowired
	Stats72Repository stats72Repo;

    @Autowired
	CardsRepository cardsRepo;
    
    HashMap<String,Tournament> map = new HashMap<>();

    public TournamentSet(){
        this.map.put("iron", new Tournament("Iron", "Iron16", "14Day","iron"));
		this.map.put("bronze", new Tournament("Bronze", "Bronze16", "14Day","bronze"));
		this.map.put("gold", new Tournament("Gold", "Gold32", "14Day", "gold"));
		this.map.put("perfectteam", new Tournament("PerfectTeam", "PerfectTeam", "AllTime","perfectteam"));
		this.map.put("perfectdraft", new Tournament("PerfectDraft", "PerfectDraft", "AllTime","perfectdraft"));
		this.map.put("dailylive", new Tournament("Daily Live", "DailyLive", "AllTime", "dailylive"));
		this.map.put("dailylivegold", new Tournament("Daily Live Gold", "DailyLiveGold", "AllTime", "dailylivegold"));
		this.map.put("dailybronzefloorcap", new Tournament("Daily Bronze Floor Cap", "DailyBronzeFloorCap", "AllTime", "dailybronzefloorcap"));
    }

    public void addTournament(Tournament tournament){
        map.put(tournament.getUrlSegment(), tournament);
    }

    public Tournament getTournamentByUrlSegment(String urlSegment){
        return map.get(urlSegment);
    }
	public Tournament getTournamentByName(String name) {
		for (HashMap.Entry<String, Tournament> mapElement : map.entrySet() ) {
            Tournament t = mapElement.getValue();
			if (t.getDisplayName().equals(name) || t.getDisplayName().toLowerCase().equals(name)) {
				return t;
			}
		}
		return null;
	}
	public Tournament getTournamentByDbName(String name) {
		for (HashMap.Entry<String, Tournament> mapElement : map.entrySet() ) {
            Tournament t = mapElement.getValue();
			if (t.getDbName().equals(name) || t.getDbName().toLowerCase().equals(name)) {
				return t;
			}
		}
		return null;
	}



    public CardStatSet getCardStatSet(String type, CardStatSet.Handed h, CardStatSet.Aggregate a){
        Tournament t = map.get(type);
        CardStatSet css = t.getCardStatSet(h, a);
        if( css == null ){
			List<CardTournamentResult> results = stats72Repo.getResultList(t.getDbName(), t.getDefaultEra().getEnd(), t.getDefaultEra().getStart());
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

			for ( CardTournamentResult result : results) {
				Card card = cardsRepo.getCard(result.getCid());
				double ip = result.getInnings();
				double pa = result.getPa().doubleValue();
				if(ip > 0){
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
				if(pa > 0){
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
			averagesvl.setControl(controlvlCounter/inningsCounter);
			averagesvl.setPbabip(pbabipvlCounter/inningsCounter);
			averagesvl.setPhr(phrvlCounter/inningsCounter);
			averagesvl.setStuff(stuffvlCounter/inningsCounter);
			averagesvl.setEye(eyevlCounter/paCounter);
			averagesvl.setBabip(babipvlCounter/paCounter);
			averagesvl.setPower(powervlCounter/paCounter);
			averagesvl.setAvk(avkvlCounter/paCounter);
			t.setCardStatSet(CardStatSet.Handed.LEFT, CardStatSet.Aggregate.AVG, averagesvl);

			averagesvr.setPhr(phrvrCounter/inningsCounter);
			averagesvr.setControl(controlvrCounter/inningsCounter);
			averagesvr.setPbabip(pbabipvrCounter/inningsCounter);
			averagesvr.setStuff(stuffvrCounter/inningsCounter);
			averagesvr.setEye(eyevrCounter/paCounter);
			averagesvr.setBabip(babipvrCounter/paCounter);
			averagesvr.setPower(powervrCounter/paCounter);
			averagesvr.setAvk(avkvrCounter/paCounter);
			t.setCardStatSet(CardStatSet.Handed.RIGHT, CardStatSet.Aggregate.AVG, averagesvr);

            map.replace(type, t, t);
            log.info("CardSet replaced");
		}
            //get the set
            //set it to the tournament object
            //return the set
        return t.getCardStatSet(h, a);
    }
}