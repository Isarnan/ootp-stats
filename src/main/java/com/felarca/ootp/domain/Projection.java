package com.felarca.ootp.domain;
/*
 * This class is for display purposes.  It will hold a card and a tournament result.
 */

import com.felarca.ootp.domain.results.CardTournamentResult;

import lombok.Getter;
import lombok.Setter;

public class Projection {
    @Getter
    @Setter
    Card card;

    @Getter
    @Setter
    CardTournamentResult ctr;

    @Getter
    @Setter
    private double hExpectedOPS;

    @Getter
    @Setter
    private double pExpectedOPS;

    
}
