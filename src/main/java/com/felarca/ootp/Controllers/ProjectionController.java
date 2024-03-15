package com.felarca.ootp.Controllers;

import java.util.ArrayList;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.Repositories.Stats72Repository;
import com.felarca.ootp.domain.Card;
import com.felarca.ootp.domain.OotpModel;
import com.felarca.ootp.domain.OotpModelSet;
import com.felarca.ootp.domain.Projection;
import com.felarca.ootp.domain.Restriction;
import com.felarca.ootp.domain.Tournament;

import lombok.extern.java.Log;

@Log
@Controller

public class ProjectionController {
    @Autowired
    OotpModelSet ts;

    @Autowired
    Stats72Repository stats72Repo;

    @Autowired
    CardsRepository cardsRepo;

    @RequestMapping("/projections")
    public String projections(Model model, @RequestParam(required = false) String modelShortName,
            @RequestParam(required = false) String tournamentShortName,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String pos) {
        // OotpModel model = ts.getTournamentByUrlSegment(modelShortName);
        ArrayList<String> messages = new ArrayList<String>();
        OotpModel mod = OotpModel.models.get(modelShortName);
        Tournament tournament = Tournament.tournaments.get(tournamentShortName);
        
        if (mod == null) {
            messages.add("Model " + modelShortName + " not found.");
            model.addAttribute("messages", messages);
            return "projections";
        }

        if (tournament == null) {
            messages.add("Tournament " + tournamentShortName + " not found.");
            model.addAttribute("messages", messages);
            return "projections";
        }

        Restriction restriction = tournament.getRestriction();

        // Query cards based on restriction.
        ArrayList<Card> cards = cardsRepo.getTournamentCards(restriction.getValueFloor(),
                restriction.getValueCeiling());

        if (pos != null) {
            ArrayList<Card> filteredList = new ArrayList<>();
            switch (pos) {
                case "c":
                    for (Card c : cards) {
                        if (c.getRatingC() > 0) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "1b":
                    for (Card c : cards) {
                        if (c.getRating1B() > 0) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "2b":
                    for (Card c : cards) {
                        if (c.getRating2B() > 0) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "3b":
                    for (Card c : cards) {
                        if (c.getRating3B() > 0) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "ss":
                    for (Card c : cards) {
                        if (c.getRatingSS() > 0) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "lf":
                    for (Card c : cards) {
                        if (c.getRatingLF() > 0) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "cf":
                    for (Card c : cards) {
                        if (c.getRatingCF() > 0) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "rf":
                    for (Card c : cards) {
                        if (c.getRatingRF() > 0) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "p":
                    for (Card c : cards) {
                        if (c.getRatingP() > 0) {
                            filteredList.add(c);
                        }
                    }
                    break;
            }
            cards = filteredList;
        }

        // Need to calculate and add the xOPS to the cards. Then I can sort them with it
        // before passing to the view.
        ArrayList<Projection> projections = new ArrayList<Projection>();
        for (Card c : cards) {
            Projection p = new Projection();
            p.setCard(c);
            p.setHExpectedOPS(mod.expectedOPS(c));
            p.setPExpectedOPS(mod.expectedOPSa(c));
            projections.add(p);
        }

        if (pos != null && pos.equals("p")) {
            projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getPExpectedOPS)));
        } else {
            projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getHExpectedOPS).reversed()));
        }
        model.addAttribute("projections", projections);
        model.addAttribute("cards", cards);
        model.addAttribute("tournament", tournament);
        model.addAttribute("model", mod);
        return "projections";

    }
}
