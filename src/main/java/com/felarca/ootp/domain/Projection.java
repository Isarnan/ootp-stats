package com.felarca.ootp.domain;
/*
 * This class is for display purposes.  It will hold a card and a tournament result.
 */

import com.felarca.ootp.domain.dao.Cards;
import com.felarca.ootp.domain.results.CardTournamentResult;

import lombok.Getter;
import lombok.Setter;

public class Projection {
    @Getter
    @Setter
    private Cards card;

    @Getter
    @Setter
    private CardTournamentResult ctr;

    @Getter
    @Setter
    private double hObservedOPS;

    @Getter
    @Setter
    private double hExpectedOPS;

    @Getter
    @Setter
    private double hExpectedOPSvL;

    @Getter
    @Setter
    private double hExpectedOPSvR;

    @Getter
    @Setter
    private double combExpectedOPS;

    @Getter
    @Setter
    private double combExpectedOPSvL;

    @Getter
    @Setter
    private double combExpectedOPSvR;

    @Getter
    @Setter
    private double pExpectedOPS;

    @Getter
    @Setter
    private double spExpectedOPS;

    @Getter
    @Setter
    private double rpExpectedOPS;

    @Getter
    @Setter
    private double pCombExpectedOPS;

    @Getter
    @Setter
    private double spCombExpectedOPS;

    @Getter
    @Setter
    private double rpCombExpectedOPS;    
}
