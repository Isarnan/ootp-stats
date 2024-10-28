package com.felarca.ootp.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.Repositories.Stats72Repository;
import com.felarca.ootp.domain.DataSet;
import com.felarca.ootp.domain.Release;
import com.felarca.ootp.domain.results.CardTournamentResult;

import lombok.extern.java.Log;

@Log
@Controller

public class DatasetController {
    @Autowired
    Stats72Repository stats72Repo;

    @Autowired
    CardsRepository cardsRepo;

    @RequestMapping("/dataset/{dsShortName}")
    public String tournament(Model model, @PathVariable String dsShortName) {
        ArrayList<String> messages = new ArrayList<String>();
        DataSet ds = new DataSet(dsShortName);
        String[] datasets = new String[] { ds.getDsShortName() };

        Release rel = Release.getReleaseByName("AllTime");

        List<CardTournamentResult> list = stats72Repo.getResultList(datasets, rel.getEnd(), rel.getStart());
        System.out.println(list.size());

        int pitchers = 0;
        int hitters = 0;
        for (CardTournamentResult ctr : list) {
            if (ctr.getBattersFaced() > 0) { // Pitchers
                pitchers++;
                if (ctr.getP_gamesstarted() == ctr.getP_games()) {
                    // Starter only records
                    ds.setStarterPitcherRecords(ds.getStarterPitcherRecords() + 1);
                } else if (ctr.getP_gamesstarted().intValue() == 0) {
                    // Only relievers
                    ds.setReliefPitcherRecords(ds.getReliefPitcherRecords() + 1);
                } else {
                    // Mixed Use
                    ds.setReliefPitcherRecords(ds.getReliefPitcherRecords() + 1);
                }
            } else {
                hitters++;
            }

            /*
             * Cards c = cardsRepo.findByCardID(ctr.getCid());
             * if (c == null) {
             * messages.add("c is nuull.  cid: " + ctr.getCid());
             * model.addAttribute("messages", messages);
             * return "index";
             * }
             * if (c.getThrows() == CardStatSet.Handed.RIGHT) {
             * rPitches += ctr.getP_pitches().intValue();
             * } else {
             * lPitches += ctr.getP_pitches().intValue();
             * }
             */
        }
        log.info("Pitchers: " + pitchers + " Hitters: " + hitters + " Records: " + list.size());

        model.addAttribute("messages", messages);
        model.addAttribute("dataset", ds);
        return "dataset";
    }
}
