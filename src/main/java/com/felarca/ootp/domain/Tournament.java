package com.felarca.ootp.domain;

import java.util.ArrayList;
import java.util.HashMap;

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
    private  int yearPlayed;

    @Setter
    @Getter
    private String ballpark;

    @Getter
    @Setter
    ArrayList<Card> eligibleCards = new ArrayList<Card>();

    public Tournament(String tournamentShortName, Restriction restriction, int yearPlayed, String ballpark){
        this.tournamentShortName = tournamentShortName;
        this.restriction = restriction;
        this.yearPlayed = yearPlayed;
        this.ballpark = ballpark;
    };

    static public HashMap<String, Tournament> tournaments = new HashMap<String, Tournament>();

}
