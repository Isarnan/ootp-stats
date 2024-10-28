package com.felarca.ootp.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.felarca.ootp.Repositories.CardsRepository;
import com.felarca.ootp.Repositories.Stats72Repository;
import com.felarca.ootp.domain.Card;
import com.felarca.ootp.domain.CardStatSet;
import com.felarca.ootp.domain.OotpModel;
import com.felarca.ootp.domain.OotpModelSet;
import com.felarca.ootp.domain.Release;
import com.felarca.ootp.domain.Tournament;
import com.felarca.ootp.domain.results.CardTournamentResult;
import com.felarca.ootp.domain.results.RawPoint;

import lombok.extern.java.Log;

@Log
@Controller

public class ModelController {
    @Autowired
    OotpModelSet ts;

    @Autowired
    Stats72Repository stats72Repo;

    @Autowired
    CardsRepository cardsRepo;

    @RequestMapping("/model/{modelName}")
    public String model(Model model, @PathVariable String modelName, @RequestParam(required = false) Integer pa,
            @RequestParam(required = false) Integer bf) {
        OotpModel t = OotpModel.models.get(modelName); // ts.getTournamentByUrlSegment(tournamenttype);
        // CardStatSet css = ts.getCardStatSet(tournamenttype, CardStatSet.Handed.RIGHT,
        // CardStatSet.Aggregate.AVG);
        // Meta meta = new Meta(tournamenttype);
        String[] datasets = t.getDatasets(); // meta.getDBTypeByURL(tournamenttype);

        ArrayList<String> messages = new ArrayList<String>();
        if (datasets == null) {
            messages.add("Datasets is null.");
            model.addAttribute("messages", messages);
            return "index";
        }

        Release era = t.getDefaultRelease();
        ;
        if (era == null) {
            messages.add("Era is null.");
            model.addAttribute("messages", messages);
            return "index";
        }

        List<CardTournamentResult> list = stats72Repo.getResultList(datasets, era.getEnd(), era.getStart());
        List<CardTournamentResult> pList = list;
        // Want to count pitches before filters.
        // We cant tell who you face...so, unfiltered makes more sense.
        int rPitches = 0, lPitches = 0;
        int lBatters = 0, rBatters = 0, sBatters = 0;
        int hitsInPlay = 0, leagueTriples = 0, leagueDoubles = 0;
        for (CardTournamentResult ctr : list) {
            Card c = cardsRepo.getCard(ctr.getCid());
            if(c == null){
                messages.add("c is nuull.  cid: " + ctr.getCid());
                model.addAttribute("messages", messages);
                return "index";
            }
            if (c.getThrows() == CardStatSet.Handed.RIGHT) {
                rPitches += ctr.getP_pitches().intValue();
            } else {
                lPitches += ctr.getP_pitches().intValue();
            }
        }
        for (CardTournamentResult ctr : list) {
            Card c = cardsRepo.getCard(ctr.getCid());
            if(c == null){
                messages.add("c is nuull.  cid: " + ctr.getCid());
                model.addAttribute("messages", messages);
                return "index";
            }
            switch (c.getBats()) {
                case LEFT:
                    lBatters += ctr.getPa().intValue();
                    break;
                case RIGHT:
                    rBatters += ctr.getPa().intValue();
                    break;
                case SWITCH:
                    sBatters += ctr.getPa().intValue();
                    break;
                default:
                    break;
            }
            hitsInPlay = hitsInPlay + ctr.getP_singles().intValue() + ctr.getP_doubles().intValue()
                    + ctr.getP_triples().intValue();
            leagueDoubles += ctr.getP_doubles().intValue();
            leagueTriples += ctr.getP_triples().intValue();
        }
        log.info("league triples: " + leagueTriples + "lg 2b: " + leagueDoubles + "lg hip: " + hitsInPlay);
        t.setLPitches(lPitches);
        t.setRPitches(rPitches);
        t.setLBatters(lBatters);
        t.setRBatters(rBatters);
        t.setSBatters(sBatters);
        t.setLeagueTripleRate((double) leagueTriples / hitsInPlay);
        t.setLeagueDoubleRate((double) leagueDoubles / hitsInPlay);

        // log.info("r: " + rPitches + " l: " + lPitches + " dbtype: " + dbType +
        // "start: " + era.getStart() + "end: " + era.getEnd());
        // Here we apply our filters. Mainly we want to get rid of small result sets.
        if (pa != null) {
            t.setPaFilter(pa);
            Predicate<CardTournamentResult> byPa = hitter -> hitter.getPa().intValue() > pa.intValue();
            list = list.stream().filter(byPa).collect(Collectors.toList());
        } else {
            Predicate<CardTournamentResult> byPa = hitter -> hitter.getPa().intValue() > t.getPaFilter();
            list = list.stream().filter(byPa).collect(Collectors.toList());
        } 
        t.setHittingModelSize(list.size());

        if (bf != null) {
            t.setBfFilter(bf);
            Predicate<CardTournamentResult> byPa = hitter -> hitter.getBattersFaced() > bf.intValue();
            pList = pList.stream().filter(byPa).collect(Collectors.toList());
        } else {
            Predicate<CardTournamentResult> byPa = hitter -> hitter.getBattersFaced() > t.getBfFilter();
            pList = pList.stream().filter(byPa).collect(Collectors.toList());
        }
        log.info("Batters Faced: " + bf + " Pitching Set: " + pList.size());
        t.setPitchingModelSize(pList.size());
        /*
         * EYE
         */
        WeightedObservedPoints obs = new WeightedObservedPoints();
        Map<Double, Double> eyeData = new TreeMap<>();
        for (CardTournamentResult ctr : list) {
            Card c = cardsRepo.getCard(ctr.getCid());
            Double effectiveEye = t.getEffectiveStat((double) c.getEyevL(), (double) c.getEyevR());
            Double walksPer100 = (ctr.getBb().doubleValue() / ctr.getPa().doubleValue()) * 100.0;
            eyeData.put(effectiveEye, walksPer100);
            WeightedObservedPoint point = new WeightedObservedPoint(1.0, effectiveEye, walksPer100);
            obs.add(point);
        }
        log.info("Lines of eye: " + eyeData.size());

        model.addAttribute("eyeData", eyeData);
        PolynomialFunction poly = getFunction(obs, t.getEyeDegree());
        t.setWalkFunction(poly);

        /*
         * Homeruns
         */
        obs.clear();
        Map<Double, Double> hrData = new TreeMap<>();
        for (CardTournamentResult ctr : list) {
            Card c = cardsRepo.getCard(ctr.getCid());
            Double effectivePower = t.getEffectiveStat((double) c.getPowervL(), (double) c.getPowervR());
            Double homerunsPer100 = (ctr.getHr().doubleValue() / ctr.getPa().doubleValue()) * 100.0;
            hrData.put(effectivePower, homerunsPer100);
            WeightedObservedPoint point = new WeightedObservedPoint(1.0, effectivePower, homerunsPer100);
            obs.add(point);
        }
        log.info("Lines of hr: " + hrData.size());
        model.addAttribute("hrData", hrData);
        poly = getFunction(obs, t.getPowerDegree());
        t.setHomerunFunction(poly);

        /*
         * AVK
         */

        obs.clear();
        Map<Double, Double> soData = new TreeMap<>();
        for (CardTournamentResult ctr : list) {
            Card c = cardsRepo.getCard(ctr.getCid());
            Double effectiveAvK = t.getEffectiveStat((double) c.getKsvL(), (double) c.getAKvR());
            Double kPer100 = (ctr.getSo().doubleValue() / ctr.getPa().doubleValue()) * 100.0;
            soData.put(effectiveAvK, kPer100);
            WeightedObservedPoint point = new WeightedObservedPoint(1.0, effectiveAvK, kPer100);
            obs.add(point);
        }
        log.info("Lines of k: " + soData.size());

        model.addAttribute("soData", soData);
        poly = getFunction(obs, t.getAvkDegree());
        t.setKFunction(poly);
        /*
         * BABIP
         */

        obs.clear();
        Map<Double, Double> babipData = new TreeMap<>();
        for (CardTournamentResult ctr : list) {
            Card c = cardsRepo.getCard(ctr.getCid());
            Double effectiveBabip = t.getEffectiveStat((double) c.getBABIPvL(), (double) c.getBABIPvR());
            Double hits = ctr.getSingles().doubleValue() + ctr.getDoubles().doubleValue()
                    + ctr.getTriples().doubleValue();
            Double bip = ctr.getPa().doubleValue() - ctr.getHr().doubleValue() - ctr.getBb().doubleValue()
                    - ctr.getSo().doubleValue();
            Double hRate = (hits / bip);
            WeightedObservedPoint point = new WeightedObservedPoint(1.0, effectiveBabip, hRate);
            obs.add(point);
            babipData.put(effectiveBabip, hRate);
        }
        log.info("Lines of babip: " + babipData.size());

        model.addAttribute("babipData", babipData);
        poly = getFunction(obs, t.getBabipDegree());
        t.setBabipFunction(poly);

        /*
         * Doubles
         */
        obs.clear();
        // List<RawPoint> rawDoublePoints = cardsRepo.getDoublePoints();
        Map<Double, Double> gapData = new TreeMap<>();
        for (CardTournamentResult ctr : list) {
            Card c = cardsRepo.getCard(ctr.getCid());
            Double effectiveGap = t.getEffectiveStat((double) c.getGapvL(), (double) c.getGapvR());
            Double hits = ctr.getSingles().doubleValue() + ctr.getDoubles().doubleValue()
                    + ctr.getTriples().doubleValue();
            Double doubles = ctr.getDoubles().doubleValue() + ctr.getTriples().doubleValue();
            Double doubleRate = doubles / hits;
            WeightedObservedPoint point = new WeightedObservedPoint(1.0, effectiveGap, doubleRate);
            obs.add(point);
            gapData.put(effectiveGap, doubleRate);
        }
        model.addAttribute("doubleData", gapData);
        poly = getFunction(obs, t.getDoubleDegree());
        t.setDoubleFunction(poly);

        /*
         * One loop for all 4 pitching stats.
         */
        WeightedObservedPoints obsControl = new WeightedObservedPoints();
        Map<Double, Double> controlData = new TreeMap<>();
        WeightedObservedPoints obsPHomerun = new WeightedObservedPoints();
        Map<Double, Double> pHomerunData = new TreeMap<>();
        WeightedObservedPoints obsK = new WeightedObservedPoints();
        Map<Double, Double> kData = new TreeMap<>();
        WeightedObservedPoints obsPBabip = new WeightedObservedPoints();
        Map<Double, Double> pBabipData = new TreeMap<>();
        for (CardTournamentResult ctr : pList) {
            Card c = cardsRepo.getCard(ctr.getCid());

            Double effectiveControl = t.getEffectiveStat((double) c.getControlvl(), (double) c.getControlvr(),
                    c.getThrows());
            Double walkRate = ctr.getP_bb().doubleValue() / ctr.getBattersFaced();
            WeightedObservedPoint point = new WeightedObservedPoint(1.0, effectiveControl, walkRate);
            obsControl.add(point);
            controlData.put(effectiveControl, walkRate);

            Double effectivePHr = t.getEffectiveStat((double) c.getPhrvl(), (double) c.getPhrvr(), c.getThrows());
            Double hrRate = ctr.getP_homeruns().doubleValue() / ctr.getBattersFaced();
            point = new WeightedObservedPoint(1.0, effectivePHr, hrRate);
            obsPHomerun.add(point);
            pHomerunData.put(effectivePHr, hrRate);

            Double effectiveStuff = t.getEffectiveStat((double) c.getStuffvl(), (double) c.getStuffvr(), c.getThrows());
            Double kRate = ctr.getK().doubleValue() / ctr.getBattersFaced();
            point = new WeightedObservedPoint(1.0, effectiveStuff, kRate);
            obsK.add(point);
            kData.put(effectiveStuff, kRate);

            Double effectivePBabip = t.getEffectiveStat((double) c.getPbabipvl(), (double) c.getPbabipvr(),
                    c.getThrows());
            Double hits = ctr.getP_singles().doubleValue() + ctr.getP_doubles().doubleValue()
                    + ctr.getP_triples().doubleValue();
            Double bip = (double) ctr.getBattersFaced() - ctr.getP_homeruns().doubleValue()
                    - ctr.getP_bb().doubleValue()
                    - ctr.getK().doubleValue();
            Double hRate = (hits / bip);
            point = new WeightedObservedPoint(1.0, effectivePBabip, hRate);
            obsPBabip.add(point);
            pBabipData.put(effectivePBabip, hRate);

            // Double and triple rates dont depend on P. So, calculate based on league in
            // play rates.

        }
        model.addAttribute("controlData", controlData);
        log.info("Lines of Control: " + controlData.size());
        model.addAttribute("pHomerunData", pHomerunData);
        model.addAttribute("kData", kData);
        model.addAttribute("pBabipData", pBabipData);

        poly = getFunction(obsControl, t.getControlDegree());
        t.setPWalkFunction(poly);
        poly = getFunction(obsPHomerun, t.getPHRDegree());
        t.setPHomerunFunction(poly);
        poly = getFunction(obsK, t.getStuffDegree());
        t.setPKFunction(poly);
        poly = getFunction(obsPBabip, t.getPBabipDegree());
        t.setPBabipFunction(poly);

        model.addAttribute("tournament", t);
        // Not sure this is needed since we expect these are setup at boot.
        // t.setModelShortName(tournamenttype);
        OotpModel.models.put(t.getModelShortName(), t);
        return "graphs";
    }

    @RequestMapping("/model")
    public String modelSelect(Model model, @RequestParam(required = false) String modelShortName,
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
        return "models";
    }

    public PolynomialFunction getFunction(List<RawPoint> rawPoints) {
        WeightedObservedPoints obs = new WeightedObservedPoints();

        // Add points here; for instance,
        for (RawPoint rp : rawPoints) {
            WeightedObservedPoint point = new WeightedObservedPoint(1.0, rp.getCardStat(), rp.getObservedStat());
            obs.add(point);
            // log.info(rp.getCardStat() + ":" + rp.getObservedStat());
        }
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(1);
        // Retrieve fitted parameters (coefficients of the polynomial function).
        final double[] coeff = fitter.fit(obs.toList());
        // for (double a : coeff) {
        // log.info("a: " + a);
        // }
        PolynomialFunction poly = new PolynomialFunction(coeff);
        // log.info("poly: " + poly.value(500) + " coeff:" + poly.toString());
        return poly;
        // double predicted value = poly.value(inputValue)

    }

    public PolynomialFunction getFunction(WeightedObservedPoints obs, int degree) {
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
        // Retrieve fitted parameters (coefficients of the polynomial function).
        final double[] coeff = fitter.fit(obs.toList());
        // for (double a : coeff) {
        // log.info("a: " + a);
        // }
        PolynomialFunction poly = new PolynomialFunction(coeff);
        // log.info("poly: " + poly.value(500) + " coeff:" + poly.toString());
        return poly;
        // double predicted value = poly.value(inputValue)

    }
}
