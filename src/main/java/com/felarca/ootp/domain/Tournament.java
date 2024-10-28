package com.felarca.ootp.domain;

import java.util.ArrayList;
import java.util.HashMap;

import com.felarca.ootp.domain.dao.StatYear;

import lombok.Getter;
import lombok.Setter;

public class Tournament {
    @Getter
    @Setter
    private String tournamentShortName;

    @Setter
    @Getter
    private Restriction restriction;

    @Setter
    @Getter
    private StatYear yearPlayed;

    @Setter
    @Getter
    private Ballpark ballpark;

    @Getter
    @Setter
    ArrayList<Card> eligibleCards = new ArrayList<Card>();

    @Getter
    @Setter
    HashMap<CardStatSet.Position, ArrayList<Projection>> versusRight = new HashMap<CardStatSet.Position, ArrayList<Projection>>();

    @Getter
    @Setter
    HashMap<CardStatSet.Position, ArrayList<Projection>> versusLeft = new HashMap<CardStatSet.Position, ArrayList<Projection>>();

    @Getter
    @Setter
    HashMap<CardStatSet.Position, HashMap<CardStatSet.DefensiveStat, Integer>> positionFilters = new HashMap<CardStatSet.Position, HashMap<CardStatSet.DefensiveStat, Integer>>();

    public Tournament(String tournamentShortName, Restriction restriction, StatYear yearPlayed, Ballpark ballpark) {
        this.tournamentShortName = tournamentShortName;
        this.restriction = restriction;
        this.yearPlayed = yearPlayed;
        this.ballpark = ballpark;
        // Default Position Filters
        positionFilters.put(CardStatSet.Position.CATCHER, new HashMap<CardStatSet.DefensiveStat, Integer>() {{ put(CardStatSet.DefensiveStat.CATCHERFRAMING, 50); }});
        positionFilters.put(CardStatSet.Position.SECONDBASE, new HashMap<CardStatSet.DefensiveStat, Integer>() {{ put(CardStatSet.DefensiveStat.INFIELDRANGE, 70); }});
        positionFilters.put(CardStatSet.Position.SHORTSTOP, new HashMap<CardStatSet.DefensiveStat, Integer>() {{ put(CardStatSet.DefensiveStat.INFIELDRANGE, 70); }});
        positionFilters.put(CardStatSet.Position.LEFTFIELD, new HashMap<CardStatSet.DefensiveStat, Integer>() {{ put(CardStatSet.DefensiveStat.OUTFIELDRANGE, 70); }});
        positionFilters.put(CardStatSet.Position.CENTERFIELD, new HashMap<CardStatSet.DefensiveStat, Integer>() {{ put(CardStatSet.DefensiveStat.OUTFIELDRANGE, 90); }});
        positionFilters.put(CardStatSet.Position.RIGHTFIELD, new HashMap<CardStatSet.DefensiveStat, Integer>() {{ put(CardStatSet.DefensiveStat.OUTFIELDRANGE, 70); }});

    };

    public void addTop10(CardStatSet.Position position, ArrayList<Projection> top10, CardStatSet.Handed handed){
        if(handed == CardStatSet.Handed.RIGHT){
            this.versusRight.put(position, top10);
        } else if(handed == CardStatSet.Handed.LEFT){
            this.versusLeft.put(position, top10);
        }

    }

    static public HashMap<String, Tournament> tournaments = new HashMap<String, Tournament>();

    public HashMap<CardStatSet.DefensiveStat, Integer> getFilter(CardStatSet.Position pos){
        return positionFilters.get(pos);
    }
    public void addFilter(CardStatSet.Position pos, CardStatSet.DefensiveStat stat, int value){
        HashMap<CardStatSet.DefensiveStat, Integer> posMap = positionFilters.get(pos);
        posMap.put(stat, value);
        positionFilters.put(pos, posMap);
    }
}
