package com.felarca.ootp.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.domain.dao.Cards;
import com.felarca.ootp.domain.dao.DailyHitter;
import com.felarca.ootp.domain.dao.DailyPitcher;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.extern.java.Log;

@Log
@Controller

public class PTLiveController {

	@Autowired
	CardsRepository cardsRepo;

	@RequestMapping("/ptlive")
	public String ptlive(Model model) {
		List<DailyHitter> hitters = new ArrayList<>();
		try {
			File file = new ClassPathResource("FantasyPros_2024_Projections_H.csv").getFile();
			CSVReader csvReader = new CSVReader(new FileReader(file.toPath().toString()));
			hitters = new CsvToBeanBuilder<DailyHitter>(csvReader).withType(DailyHitter.class).withSkipLines(1).build()
					.parse();
		} catch (FileNotFoundException ex) {
			System.out.println(ex);

		} catch (IOException ex) {
			System.out.println(ex);
		}

		System.out.println("Hitters in CSV: " + hitters.size());
		for (DailyHitter hitter : hitters) {
			if (hitter.getPoints() == 0){
				// System.out.println("Zero points for: " + hitter.getName());
				continue;
			}
			String[] splited = hitter.getName().split("\\s+");
			ArrayList<Cards> cards = cardsRepo.findByFnAndLnAndCardType(splited[0], splited[1], 1);
			if( cards.size() == 0 && splited.length == 3){
				System.out.println(splited);
				cards = cardsRepo.findByFnAndLnAndCardType(splited[0], splited[1] + " " + splited[2], 1);
			}

			if( cards.size() == 0 && splited.length == 4){
				cards = cardsRepo.findByFnAndLnAndCardType(splited[0], splited[1] + " " + splited[2] + " " + splited[3], 1);
			}

			if (cards == null){
				System.out.println("Could not find card for: " + hitter.getName());
				continue;
			}
			if (cards.size() > 1){
				for(Cards card : cards ){
					System.out.println(card.getFn() + " " + card.getLn() + " " + card.getCardType() + " = " + hitter.getName());
				}
				hitter.setOotpPosition(-77);
				hitter.setOverall(cards.size());
			}
			if (cards != null && cards.size() == 1) {
				hitter.setOotpPosition(cards.get(0).getPosition());
				hitter.setOverall(cards.get(0).getOverall());
			}
		}

		if (hitters.size() > 0) {
			hitters.sort(Comparator.nullsFirst(Comparator.comparing(DailyHitter::getPoints).reversed()));
		}

		model.addAttribute("hitters", hitters);

		List<DailyPitcher> pitchers = new ArrayList<>();
		try {
			File file = new ClassPathResource("FantasyPros_2024_Projections_P.csv").getFile();
			CSVReader csvReader = new CSVReader(new FileReader(file.toPath().toString()));
			pitchers = new CsvToBeanBuilder<DailyPitcher>(csvReader).withType(DailyPitcher.class).withSkipLines(1).build()
					.parse();
		} catch (FileNotFoundException ex) {
			System.out.println(ex);

		} catch (IOException ex) {
			System.out.println(ex);
		}

		for (DailyPitcher pitcher : pitchers) {
			if (pitcher.getPoints() == 0)
				continue;
			String[] splited = pitcher.getName().split("\\s+");
			ArrayList<Cards> cards = cardsRepo.findByFnAndLnAndCardType(splited[0], splited[1], 1);
			if( cards.size() == 0 && splited.length == 3){
				System.out.println(splited);
				cards = cardsRepo.findByFnAndLnAndCardType(splited[0], splited[1] + " " + splited[2], 1);
			}

			if( cards.size() == 0 && splited.length == 4){
				System.out.println(splited);
				cards = cardsRepo.findByFnAndLnAndCardType(splited[0], splited[1] + " " + splited[2] + " " + splited[3], 1);
			}

			if (cards == null){
				continue;
			}
			if (cards.size() > 1){
				for(Cards card : cards ){
					System.out.println(card.getFn() + " " + card.getLn() + " " + card.getCardType() + " = " + pitcher.getName());
				}
				pitcher.setOotpPosition(-77);
				pitcher.setOverall(cards.size());
			}
			if (cards != null && cards.size() == 1) {
				pitcher.setOotpPosition(cards.get(0).getPosition());
				pitcher.setOverall(cards.get(0).getOverall());
				pitcher.setPitcherRole(cards.get(0).getPitcherRole());
			}
		}

		if (pitchers.size() > 0) {
			pitchers.sort(Comparator.nullsFirst(Comparator.comparing(DailyPitcher::getPoints).reversed()));
		}

		System.out.println(pitchers.size());

		model.addAttribute("pitchers", pitchers);


		return "ptlive";
	}

}
