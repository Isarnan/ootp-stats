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
import com.felarca.ootp.domain.CardStatSet;
import com.felarca.ootp.domain.CardType;
import com.felarca.ootp.domain.OotpModel;
import com.felarca.ootp.domain.OotpModelSet;
import com.felarca.ootp.domain.Projection;
import com.felarca.ootp.domain.Restriction;
import com.felarca.ootp.domain.StatAdjustment;
import com.felarca.ootp.domain.Tournament;
import com.felarca.ootp.domain.dao.Cards;

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
            @RequestParam(required = false) String pos,
            @RequestParam(required = false) Integer iRange,
            @RequestParam(required = false) Integer oRange,
            @RequestParam(required = false) Integer framing,
            @RequestParam(required = false) Integer stamina,
            @RequestParam(required = false) Integer control,
            @RequestParam(required = false) String owned) {
        // OotpModel model = ts.getTournamentByUrlSegment(modelShortName);
        ArrayList<String> messages = new ArrayList<String>();
        OotpModel mod = OotpModel.models.get(modelShortName);
        Tournament tournament = Tournament.tournaments.get(tournamentShortName);

        if (mod == null ) {
            messages.add("Model " + modelShortName + " not found.");
            model.addAttribute("messages", messages);
            return "projections";
        }

        if (tournament == null) {
            messages.add("Tournament " + tournamentShortName + " not found.");
            model.addAttribute("messages", messages);
            return "projections";
        }

        StatAdjustment adj = new StatAdjustment(mod.getStatYear(), tournament.getYearPlayed(), mod.getBallpark(), tournament.getBallpark());
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
        //ArrayList<Cards> cardList = cardsRepo.findByOverallBetween(restriction.getValueFloor(),restriction.getValueCeiling());
        ArrayList<Cards> cardList = cardsRepo.findByOverallBetweenAndYearBetween(restriction.getValueFloor(),restriction.getValueCeiling(), restriction.getFirstYear(), restriction.getLastYear());

        //ArrayList<Card> cards = cardsRepo.getTournamentCards(restriction.getValueFloor(), restriction.getValueCeiling());
        //log.info("Old Cards: " + cards.size()  + " New cards: " + cardList.size());
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
        if (pos != null) {
            ArrayList<Cards> filteredList = new ArrayList<>();
            switch (pos) {
                case "c":
                    for (Cards c : cardList) {
                        if (c.getRatingC() > 0|| c.getLearnC() == 1) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "1b":
                    for (Cards c : cardList) {
                        if (c.getRating1B() > 0|| c.getLearn1B() == 1) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "2b":
                    for (Cards c : cardList) {
                        if (c.getRating2B() > 0|| c.getLearn2B() == 1) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "3b":
                    for (Cards c : cardList) {
                        if (c.getRating3B() > 0|| c.getLearn3B() == 1) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "ss":
                    for (Cards c : cardList) {
                        if (c.getRatingSS() > 0|| c.getLearnSS() == 1) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "lf":
                    for (Cards c : cardList) {
                        if (c.getRatingLF() > 0|| c.getLearnLF() == 1) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "cf":
                    for (Cards c : cardList) {
                        if (c.getRatingCF() > 0|| c.getLearnCF() == 1) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "rf":
                    for (Cards c : cardList) {
                        if (c.getRatingRF() > 0 || c.getLearnRF() == 1) {
                            filteredList.add(c);
                        }
                    }
                    break;
                case "p":
                    for (Cards c : cardList) {
                        if (c.getRatingP() > 0) {
                            filteredList.add(c);
                        }
                    }
                    break;
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

        if (iRange != null) {
            ArrayList<Cards> filteredList = new ArrayList<>();
            for (Cards c : cardList) {
                if (c.getIfr() > iRange) {
                    filteredList.add(c);
                }
            }
            cardList = filteredList;
        }

        if (oRange != null) {
            ArrayList<Cards> filteredList = new ArrayList<>();
            for (Cards c : cardList) {
                if (c.getOfRange() > oRange) {
                    filteredList.add(c);
                }
            }
            cardList = filteredList;
        }

        if (framing != null) {
            ArrayList<Cards> filteredList = new ArrayList<>();
            for (Cards c : cardList) {
                if (c.getCatcherFrame() > framing) {
                    filteredList.add(c);
                }
            }
            cardList = filteredList;
        }

        if (stamina != null) {
            ArrayList<Cards> filteredList = new ArrayList<>();
            for (Cards c : cardList) {
                if (c.getStamina() > stamina) {
                    filteredList.add(c);
                }
            }
            cardList = filteredList;
        }

        if (control != null) {
            ArrayList<Cards> filteredList = new ArrayList<>();
            for (Cards c : cardList) {
                if (c.getControl() > control) {
                    filteredList.add(c);
                }
            }
            cardList = filteredList;
        }

        // Need to calculate and add the xOPS to the cards. Then I can sort them with it
        // before passing to the view.
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

        if (pos != null && pos.equals("p") && sort == null) {
            projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getPExpectedOPS)));
        } else if ( sort != null && sort.equals("ccops")){
            projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getCombExpectedOPS).reversed()));
        } else if ( sort != null && sort.equals("xopsvl")){
            projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getHExpectedOPSvL).reversed()));
        } else if ( sort != null && sort.equals("xopsvr")){
            projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getHExpectedOPSvR).reversed()));
        } else if ( sort != null && sort.equals("ccopsvl")){
            projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getCombExpectedOPSvL).reversed()));
        } else if ( sort != null && sort.equals("ccopsvr")){
            projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getCombExpectedOPSvR).reversed()));
        } else if ( sort != null && sort.equals("sp")){
            projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getSpExpectedOPS)));
        } else {
            projections.sort(Comparator.nullsFirst(Comparator.comparing(Projection::getHExpectedOPS).reversed()));
        }
        model.addAttribute("projections", projections);
        model.addAttribute("cards", cardList);
        return "projections";

    }
}
