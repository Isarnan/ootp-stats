package com.felarca.ootp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.domain.OotpModel;
import com.felarca.ootp.domain.Tournament;

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
}
