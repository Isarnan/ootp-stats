package com.felarca.ootp.Controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.domain.CardStatSet;
import com.felarca.ootp.domain.CardType;
import com.felarca.ootp.domain.DataSet;
import com.felarca.ootp.domain.OotpModel;
import com.felarca.ootp.domain.Projection;
import com.felarca.ootp.domain.Restriction;
import com.felarca.ootp.domain.StatAdjustment;
import com.felarca.ootp.domain.Tournament;
import com.felarca.ootp.domain.dao.Cards;

import lombok.extern.java.Log;

@Log
@Controller

public class TournamentController {
    @Autowired
    CardsRepository cardsRepo;

    @RequestMapping("/tournament")
    public String tournament(Model model, @RequestParam(required = false) String modelShortName,
            @RequestParam(required = false) String tournamentShortName) {
        OotpModel m = OotpModel.models.get(modelShortName);
        Tournament t = Tournament.tournaments.get(tournamentShortName);

        if (m != null) {
            log.info("lines: " + m.getModelShortName());
            model.addAttribute("model", m);
        } else {
            log.info("M is null: ");
        }

        model.addAttribute("tournament", t);
        return "tournaments";
    }

    @RequestMapping("/tournament/{tournamentShortName}/team")
    public String team(Model model, @PathVariable String tournamentShortName,
            @RequestParam(required = false) String modelShortName,
            @RequestParam(required = false) String dataSet, 
            @RequestParam(required = false) String owned) {
        ArrayList<String> messages = new ArrayList<String>();
        Tournament tournament = Tournament.tournaments.get(tournamentShortName);

        OotpModel mod = OotpModel.models.get(modelShortName);
        DataSet ds = new DataSet(dataSet);

        if (mod != null) {
            log.info("lines: " + mod.getModelShortName());
            model.addAttribute("model", mod);
        } else {
            log.info("M is null: ");
        }

        if (ds.getDsShortName() == null || ds.getDsShortName().length() > 0) {
            log.info("ds not implemented.  need to create a model based on this ds alone.");
        } else {
            log.info("ds is null: ");
        }

        StatAdjustment adj = new StatAdjustment(mod.getStatYear(), tournament.getYearPlayed(), mod.getBallpark(),
                tournament.getBallpark());
        model.addAttribute("adj", adj);
        model.addAttribute("tournament", tournament);
        model.addAttribute("model", mod);

        if (mod.getRPitches() == 0) {
            messages.add("Model " + modelShortName + " has not been calculated.");
            model.addAttribute("messages", messages);
            return "projections";
        }

        Restriction restriction = tournament.getRestriction();

        // Query cards based on restriction.
        // ArrayList<Cards> cardList =
        // cardsRepo.findByOverallBetween(restriction.getValueFloor(),restriction.getValueCeiling());
        ArrayList<Cards> cardList = cardsRepo.findByOverallBetweenAndYearBetween(restriction.getValueFloor(),
                restriction.getValueCeiling(), restriction.getFirstYear(), restriction.getLastYear());

        // ArrayList<Card> cards =
        // cardsRepo.getTournamentCards(restriction.getValueFloor(),
        // restriction.getValueCeiling());
        // log.info("Old Cards: " + cards.size() + " New cards: " + cardList.size());
        ArrayList<CardType> types = restriction.getTypes();
        if (types != null) {
            ArrayList<Cards> filteredList = new ArrayList<>();
            for (Cards c : cardList) {
                long cardType = c.getCardType();
                long cardSubType = c.getCardSubType();
                CardType type = CardType.getCardType(cardType, cardSubType);
                if (types.contains(type)) {
                    filteredList.add(c);
                }
            }
            cardList = filteredList;
        }

        if (owned != null) {
            ArrayList<Cards> filteredList = new ArrayList<>();
            for (Cards c : cardList) {
                if (c.getOwned() > 0) {
                    filteredList.add(c);
                }
            }
            cardList = filteredList;
        }

        ArrayList<Projection> projections = new ArrayList<Projection>();
        for (Cards c : cardList) {
            Projection p = new Projection();
            p.setCard(c);

            p.setHObservedOPS(mod.expectedOPS(c, adj));

            p.setHExpectedOPS(mod.expectedOPS(c, adj));
            p.setHExpectedOPSvL(mod.expectedOPS(c, CardStatSet.Handed.LEFT, adj));
            p.setHExpectedOPSvR(mod.expectedOPS(c, CardStatSet.Handed.RIGHT, adj));

            p.setCombExpectedOPS(mod.expectedCombOPS(c, adj));
            p.setCombExpectedOPSvL(mod.expectedCombOPS(c, CardStatSet.Handed.LEFT, adj));
            p.setCombExpectedOPSvR(mod.expectedCombOPS(c, CardStatSet.Handed.RIGHT, adj));

            p.setPExpectedOPS(mod.expectedOPSa(c, "P", adj));
            p.setSpExpectedOPS(mod.expectedOPSa(c, "SP", adj));

            p.setPCombExpectedOPS(mod.expectedCombOPSa(c, "P", adj));
            p.setSpCombExpectedOPS(mod.expectedCombOPSa(c, "SP", adj));

            projections.add(p);
        }

        projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getHExpectedOPSvL).reversed()));
        ArrayList<Projection> catcher = new ArrayList<Projection>();
        ArrayList<Projection> firstBase = new ArrayList<Projection>();
        ArrayList<Projection> secondBase = new ArrayList<Projection>();
        ArrayList<Projection> thirdBase = new ArrayList<Projection>();
        ArrayList<Projection> leftField = new ArrayList<Projection>();
        ArrayList<Projection> centerField = new ArrayList<Projection>();
        ArrayList<Projection> rightField = new ArrayList<Projection>();
        ArrayList<Projection> shortstop = new ArrayList<Projection>();
        ArrayList<Projection> dh = new ArrayList<Projection>();


        for (Projection p : projections) {
            Cards c = p.getCard();
            if ((c.getRatingC() > 0 || c.getLearnC() > 0) && catcher.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament.getFilter(CardStatSet.Position.CATCHER);
                if ((map.get(CardStatSet.DefensiveStat.CATCHERFRAMING)) < c.getCatcherFrame()) {
                    catcher.add(p);
                }
            }

            if ((c.getRating1B() > 0 || c.getLearn1B() > 0) && firstBase.size() < 10) {
                firstBase.add(p);
            }

            if ((c.getRating2B() > 0 || c.getLearn2B() > 0) && secondBase.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament.getFilter(CardStatSet.Position.SECONDBASE);
                if ((map.get(CardStatSet.DefensiveStat.INFIELDRANGE)) < c.getIfr()) {
                    secondBase.add(p);
                }
            }

            if ((c.getRating3B() > 0 || c.getLearn3B() > 0) && thirdBase.size() < 10) {
                thirdBase.add(p);
            }

            if ((c.getRatingSS() > 0 || c.getLearnSS() > 0) && shortstop.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament.getFilter(CardStatSet.Position.SHORTSTOP);
                if ((map.get(CardStatSet.DefensiveStat.INFIELDRANGE)) < c.getIfr()) {
                    shortstop.add(p);
                }
            }

            if ((c.getRatingLF() > 0 || c.getLearnLF() > 0) && leftField.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament.getFilter(CardStatSet.Position.LEFTFIELD);
                if ((map.get(CardStatSet.DefensiveStat.OUTFIELDRANGE)) < c.getOfRange()) {
                    leftField.add(p);
                }
            }

            if ((c.getRatingCF() > 0 || c.getLearnCF() > 0) && centerField.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament
                        .getFilter(CardStatSet.Position.CENTERFIELD);
                if ((map.get(CardStatSet.DefensiveStat.OUTFIELDRANGE)) < c.getOfRange()) {
                    centerField.add(p);
                }
            }

            if ((c.getRatingRF() > 0 || c.getLearnRF() > 0) && rightField.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament
                        .getFilter(CardStatSet.Position.RIGHTFIELD);
                if ((map.get(CardStatSet.DefensiveStat.OUTFIELDRANGE)) < c.getOfRange()) {
                    rightField.add(p);
                }
            }

            if (dh.size() < 10) {
                dh.add(p);
            }

        }
        tournament.addTop10(CardStatSet.Position.DESIGNATEDHITTER, dh, CardStatSet.Handed.LEFT);
        tournament.addTop10(CardStatSet.Position.RIGHTFIELD, rightField, CardStatSet.Handed.LEFT);
        tournament.addTop10(CardStatSet.Position.CENTERFIELD, centerField, CardStatSet.Handed.LEFT);
        tournament.addTop10(CardStatSet.Position.LEFTFIELD, leftField, CardStatSet.Handed.LEFT);
        tournament.addTop10(CardStatSet.Position.SHORTSTOP, shortstop, CardStatSet.Handed.LEFT);
        tournament.addTop10(CardStatSet.Position.THIRDBASE, thirdBase, CardStatSet.Handed.LEFT);
        tournament.addTop10(CardStatSet.Position.SECONDBASE, secondBase, CardStatSet.Handed.LEFT);
        tournament.addTop10(CardStatSet.Position.FIRSTBASE, firstBase, CardStatSet.Handed.LEFT);
        tournament.addTop10(CardStatSet.Position.CATCHER, catcher, CardStatSet.Handed.LEFT);

        projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getHExpectedOPSvR).reversed()));

        catcher = new ArrayList<Projection>();
        firstBase = new ArrayList<Projection>();
        secondBase = new ArrayList<Projection>();
        thirdBase = new ArrayList<Projection>();
        leftField = new ArrayList<Projection>();
        centerField = new ArrayList<Projection>();
        rightField = new ArrayList<Projection>();
        shortstop = new ArrayList<Projection>();
        dh = new ArrayList<Projection>();


        for (Projection p : projections) {
            Cards c = p.getCard();
            if ((c.getRatingC() > 0 || c.getLearnC() > 0) && catcher.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament.getFilter(CardStatSet.Position.CATCHER);
                if ((map.get(CardStatSet.DefensiveStat.CATCHERFRAMING)) < c.getCatcherFrame()) {
                    catcher.add(p);
                }
            }

            if ((c.getRating1B() > 0 || c.getLearn1B() > 0) && firstBase.size() < 10) {
                firstBase.add(p);
            }

            if ((c.getRating2B() > 0 || c.getLearn2B() > 0) && secondBase.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament.getFilter(CardStatSet.Position.SECONDBASE);
                if ((map.get(CardStatSet.DefensiveStat.INFIELDRANGE)) < c.getIfr()) {
                    secondBase.add(p);
                }
            }

            if ((c.getRating3B() > 0 || c.getLearn3B() > 0) && thirdBase.size() < 10) {
                thirdBase.add(p);
            }

            if ((c.getRatingSS() > 0 || c.getLearnSS() > 0) && shortstop.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament.getFilter(CardStatSet.Position.SHORTSTOP);
                if ((map.get(CardStatSet.DefensiveStat.INFIELDRANGE)) < c.getIfr()) {
                    shortstop.add(p);
                }
            }

            if ((c.getRatingLF() > 0 || c.getLearnLF() > 0) && leftField.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament.getFilter(CardStatSet.Position.LEFTFIELD);
                if ((map.get(CardStatSet.DefensiveStat.OUTFIELDRANGE)) < c.getOfRange()) {
                    leftField.add(p);
                }
            }

            if ((c.getRatingCF() > 0 || c.getLearnCF() > 0) && centerField.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament
                        .getFilter(CardStatSet.Position.CENTERFIELD);
                if ((map.get(CardStatSet.DefensiveStat.OUTFIELDRANGE)) < c.getOfRange()) {
                    centerField.add(p);
                }
            }

            if ((c.getRatingRF() > 0 || c.getLearnRF() > 0) && rightField.size() < 10) {
                HashMap<CardStatSet.DefensiveStat, Integer> map = tournament
                        .getFilter(CardStatSet.Position.RIGHTFIELD);
                if ((map.get(CardStatSet.DefensiveStat.OUTFIELDRANGE)) < c.getOfRange()) {
                    rightField.add(p);
                }
            }

            if (dh.size() < 10) {
                dh.add(p);
            }

        }
        tournament.addTop10(CardStatSet.Position.DESIGNATEDHITTER, dh, CardStatSet.Handed.RIGHT);
        tournament.addTop10(CardStatSet.Position.RIGHTFIELD, rightField, CardStatSet.Handed.RIGHT);
        tournament.addTop10(CardStatSet.Position.CENTERFIELD, centerField, CardStatSet.Handed.RIGHT);
        tournament.addTop10(CardStatSet.Position.LEFTFIELD, leftField, CardStatSet.Handed.RIGHT);
        tournament.addTop10(CardStatSet.Position.SHORTSTOP, shortstop, CardStatSet.Handed.RIGHT);
        tournament.addTop10(CardStatSet.Position.THIRDBASE, thirdBase, CardStatSet.Handed.RIGHT);
        tournament.addTop10(CardStatSet.Position.SECONDBASE, secondBase, CardStatSet.Handed.RIGHT);
        tournament.addTop10(CardStatSet.Position.FIRSTBASE, firstBase, CardStatSet.Handed.RIGHT);
        tournament.addTop10(CardStatSet.Position.CATCHER, catcher, CardStatSet.Handed.RIGHT);


        projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getSpExpectedOPS)));

        ArrayList<Projection> startingPitcher = new ArrayList<Projection>();

        for (Projection p : projections) {
            Cards c = p.getCard();
            if ( c.getRatingP() > 0 && c.getStamina() > 60  && startingPitcher.size() < 50) {
                    startingPitcher.add(p);
            }
        }

        tournament.addTop10(CardStatSet.Position.STARTINGPITCHER, startingPitcher, CardStatSet.Handed.RIGHT);

        projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getPExpectedOPS)));

        ArrayList<Projection> reliefPitcher = new ArrayList<Projection>();

        for (Projection p : projections) {
            Cards c = p.getCard();
            if ( c.getRatingP() > 0 && c.getStamina() < 60  && reliefPitcher.size() < 50) {
                    reliefPitcher.add(p);
            }
        }

        tournament.addTop10(CardStatSet.Position.RELIEFPITCHER, reliefPitcher, CardStatSet.Handed.RIGHT);


        // With the filtered list, we should project and sort
        // then we need to iterate the list and put them in categorites
        // categories are 3b vl and CF vr
        // how to setup those buckets.

        model.addAttribute("tournament", tournament);
        return "tournamentTeam";
    }
}
