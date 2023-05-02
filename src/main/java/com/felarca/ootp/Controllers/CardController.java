package com.felarca.ootp.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.domain.TierPosition;

import lombok.extern.java.Log;

@Log
@Controller

public class CardController {
	@Autowired
	CardsRepository cardsRepo;

    @RequestMapping("/card/tierpos")    
	public String tierpos(Model model) {	
        //List<TierPosition> tpos = cardsRepo.getTeamCardTierPosition("Vlad News Bears");
        List<TierPosition> tpos = cardsRepo.getCardTierPosition();
        Integer[][] matrix = new Integer[6][10];

        for(TierPosition current : tpos){
            int row = (int)current.getTier();
            int col = (int)current.getPosition();
            matrix[row][col-1] = (int)current.getCount();
        }
        
        // Add outstanding defenders
        List<TierPosition> ss = cardsRepo.getSSDefenders();
        log.info("lines: " + ss.size());
        model.addAttribute("ss", ss);
        model.addAttribute("matrix", matrix);
		return "tierpos";
	}
}
