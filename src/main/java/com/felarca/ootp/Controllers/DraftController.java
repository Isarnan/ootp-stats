package com.felarca.ootp.Controllers;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.Repositories.Stats72Repository;
import com.felarca.ootp.domain.CardStatSet;
import com.felarca.ootp.domain.Era;
import com.felarca.ootp.domain.Meta;
import com.felarca.ootp.domain.Tournament;
import com.felarca.ootp.domain.TournamentSet;
import com.felarca.ootp.domain.results.CardTournamentResult;

import lombok.extern.java.Log;

@Log
@Controller

public class DraftController {
    @Autowired
    CardsRepository cardsRepo;
    @Autowired
    Stats72Repository stats72Repo;
    @Autowired
    TournamentSet ts;

    @RequestMapping("/draft/{round}")
    public String tierpos(Model model, @PathVariable String round, @RequestParam(required = false) Integer ip,
            @RequestParam(required = false) Integer pig,
            @RequestParam(required = false) String filter, @RequestParam(required = false) String time) {
        String tournamenttype = "PerfectDraft";

        Meta meta = new Meta(tournamenttype);
        meta.setRound(round);
        Tournament t = ts.getTournamentByDbName(tournamenttype);
        Era era;
        if (time == null) {            
            era = t.getDefaultEra();
        } else {
            era = meta.getEraByName(time);
        }
        log.info(t.getDbName() + ": " + time + ": + era.getStart()");

        List<CardTournamentResult> list = stats72Repo.getResultList(t.getDbName(), era.getEnd(), era.getStart());
        if (ip != null) {
            Predicate<CardTournamentResult> byIp = hitter -> hitter.getInnings().intValue() > ip.intValue();
            list = list.stream().filter(byIp).collect(Collectors.toList());
        } else {
            Predicate<CardTournamentResult> byIp = hitter -> hitter.getInnings().intValue() > 1;
            list = list.stream().filter(byIp).collect(Collectors.toList());
        }
        if (pig != null) {
            Predicate<CardTournamentResult> byPig = hitter -> Double.valueOf(hitter.getPig()) > pig.intValue();
            list = list.stream().filter(byPig).collect(Collectors.toList());
        } else {
            Predicate<CardTournamentResult> byPig = hitter -> Double.valueOf(hitter.getPig()) > 1;
            list = list.stream().filter(byPig).collect(Collectors.toList());
        }

        List<CardTournamentResult> list2 = stats72Repo.getResultList(t.getDbName(), era.getEnd(), era.getStart());
        Integer pa = 100;
        Predicate<CardTournamentResult> byPa = hitter -> hitter.getPa().intValue() > pa.intValue();
        list2 = list2.stream().filter(byPa).collect(Collectors.toList());
        list2.sort(Comparator.nullsFirst(Comparator.comparing(CardTournamentResult::getOps).reversed()));

        int greaterThen = 0;
        int lessThen = 0;
        switch (round) {
            case "iron":
                greaterThen = 0;
                lessThen = 60;
                break;
            case "bronze":
                greaterThen = 60;
                lessThen = 70;
                break;
            case "silver":
                greaterThen = 70;
                lessThen = 80;
                break;
            case "gold":
                greaterThen = 80;
                lessThen = 90;
                break;
            case "diamond":
                greaterThen = 90;
                lessThen = 100;
                break;
            case "perfect":
                greaterThen = 100;
                lessThen = 101;
                break;

        }

        // Sorting
        list.sort(Comparator.nullsFirst(Comparator.comparing(CardTournamentResult::getEra)));

        //Need to get rid of this
        CardStatSet css = ts.getCardStatSet("perfectdraft", CardStatSet.Handed.RIGHT, CardStatSet.Aggregate.AVG);
        
        log.info("List2: " + list2.size());
        model.addAttribute("tournament", t);
        model.addAttribute("meta", meta);
        model.addAttribute("pitchers", list);
        model.addAttribute("hitters", list2);

        model.addAttribute("greaterThen", greaterThen);
        model.addAttribute("lessThen", lessThen);
        // model.addAttribute("owned", stats2Repo);
        model.addAttribute("cards", cardsRepo);
        return "draft";
    }
}
