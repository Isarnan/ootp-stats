package com.felarca.ootp.Controllers;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.Repositories.Stats72Repository;
import com.felarca.ootp.domain.CardStatSet;
import com.felarca.ootp.domain.Tournament;
import com.felarca.ootp.domain.TournamentSet;
import com.felarca.ootp.domain.results.RawPoint;

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
 
        List<RawPoint> rawEyePoints = cardsRepo.getEyePoints();
        Map<Long, Double> eyeData = new TreeMap<>();
        for (RawPoint p : rawEyePoints){
            eyeData.put(p.getCardStat(), p.getObservedStat100());
        }
        log.info("Lines of eye: " + eyeData.size());

        model.addAttribute("eyeData", eyeData);
        PolynomialFunction poly = getFunction(rawEyePoints);
        t.setWalkFunction(poly);

        List<RawPoint> rawHomerunPoints = cardsRepo.getHomerunPoints();
        Map<Long, Double> hrData = new TreeMap<>();
        for (RawPoint p : rawHomerunPoints){
            hrData.put(p.getCardStat(), p.getObservedStat100());
        }
        log.info("Lines of hr: " + hrData.size());

        model.addAttribute("hrData", hrData);
        poly = getFunction(rawHomerunPoints);
        t.setHomerunFunction(poly);

        List<RawPoint> rawKPoints = cardsRepo.getKPoints();
        Map<Long, Double> kData = new TreeMap<>();
        for (RawPoint p : rawKPoints){
            kData.put(p.getCardStat(), p.getObservedStat100());
        }
        log.info("Lines of k: " + kData.size());

        model.addAttribute("kData", kData);
        poly = getFunction(rawKPoints);
        t.setKFunction(poly);

        List<RawPoint> rawBabipPoints = cardsRepo.getBabipPoints();
        Map<Long, Double> babipData = new TreeMap<>();
        for (RawPoint p : rawBabipPoints){
            babipData.put(p.getCardStat(), p.getObservedStat());
        }
        log.info("Lines of babip: " + babipData.size());

        model.addAttribute("babipData", babipData);
        poly = getFunction(rawBabipPoints);
        t.setBabipFunction(poly);

        List<RawPoint> rawDoublePoints = cardsRepo.getDoublePoints();
        Map<Long, Double> doubleData = new TreeMap<>();
        for (RawPoint p : rawDoublePoints){
            doubleData.put(p.getCardStat(), p.getObservedStat());
        }
        log.info("Lines of 2b: " + doubleData.size());

        model.addAttribute("doubleData", doubleData);
        poly = getFunction(rawDoublePoints);
        t.setDoubleFunction(poly);

        t.setLPitches(cardsRepo.getPitches(2));
        t.setRPitches(cardsRepo.getPitches(1));

        
/*





        List<RawPoint> rawKPoints = cardsRepo.getKPoints();
        Map<Long, Double> kData = new TreeMap<>();
        for (RawPoint p : rawKPoints){
            kData.put(p.getCardStat(), p.getObservedStat100());
        }
        model.addAttribute("kData", kData);

        List<RawPoint> rawPoints = cardsRepo.getPoints();
        Map<Long, Double> graphData = new TreeMap<>();
        for (RawPoint p : rawPoints){
            graphData.put(p.getCardStat(), p.getObservedStat100());
        }
        model.addAttribute("chartData", graphData);

        List<RawPoint> rawBabipPoints = cardsRepo.getBabipPoints();
        Map<Long, Double> babipData = new TreeMap<>();
        for (RawPoint p : rawBabipPoints){
            babipData.put(p.getCardStat(), p.getObservedStat());
        }
        model.addAttribute("babipData", babipData);

        List<RawPoint> rawDoublePoints = cardsRepo.getDoublePoints();
        Map<Long, Double> doubleData = new TreeMap<>();
        for (RawPoint p : rawDoublePoints){
            doubleData.put(p.getCardStat(), p.getObservedStat100());
        }
        model.addAttribute("doubleData", doubleData);

        List<RawPoint> rawTriplePoints = cardsRepo.getTriplePoints();
        Map<Long, Double> tripleData = new TreeMap<>();
        for (RawPoint p : rawTriplePoints){
            log.info("card: " + p.getCardStat() + " stat: " + p.getObservedStat());
            tripleData.put(p.getCardStat(), p.getObservedStat());
        }
        model.addAttribute("tripleData", tripleData);
        ts.addTournament(t);
*/
		return "graphs";
	}

    public PolynomialFunction getFunction(List<RawPoint> rawPoints){
        WeightedObservedPoints obs = new WeightedObservedPoints();

        // Add points here; for instance,
        for (RawPoint rp : rawPoints) {
            WeightedObservedPoint point = new WeightedObservedPoint(1.0, rp.getCardStat(), rp.getObservedStat());
            obs.add(point);
            //log.info(rp.getCardStat() + ":" + rp.getObservedStat());
        }
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(1);
        // Retrieve fitted parameters (coefficients of the polynomial function).
        final double[] coeff = fitter.fit(obs.toList());
        for(double a : coeff){
            log.info("a: " + a);
        }
        PolynomialFunction poly = new PolynomialFunction(coeff);
        log.info("poly: " + poly.value(500) + " coeff:" + poly.toString());
        return poly;
        //double predicted value = poly.value(inputValue)

    }
}
