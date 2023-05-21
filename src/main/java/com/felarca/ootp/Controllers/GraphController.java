package com.felarca.ootp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.Repositories.Stats72Repository;
import com.felarca.ootp.domain.CardStatSet;
import com.felarca.ootp.domain.Tournament;
import com.felarca.ootp.domain.TournamentSet;

import lombok.extern.java.Log;

@Log
@Controller

public class GraphController {
    @Autowired
    TournamentSet ts;

    @Autowired
	Stats72Repository stats72Repo;

    @Autowired
	CardsRepository cardsRepo;

    @RequestMapping("/graphs")    
	public String tierpos(Model model) {	
        Tournament t = ts.getTournamentByUrlSegment("bronze");
        CardStatSet css = ts.getCardStatSet("bronze", CardStatSet.Handed.RIGHT, CardStatSet.Aggregate.AVG);
        //CardStatSet css = t.getCardStatSet(CardStatSet.Handed.RIGHT, CardStatSet.Aggregate.AVG, stats72Repo, cardsRepo );
        log.info("DbName" + t.getDbName());
        log.info("Control " + css.getControl());
		return "index";
	}


}
