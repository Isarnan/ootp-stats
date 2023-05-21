package com.felarca.ootp.domain;

import lombok.Getter;
import lombok.Setter;

public class MetaCard {



/*
    public CardStatSet getCardStatSet(MetaCard.handed h, MetaCard.aggregate a){
        if( this.cardStatSets[h.ordinal()][a.ordinal()] == null){
                List<CardTournamentResult> results = stats72Repo.getResultList(this., era.getEnd(), era.getStart());
                double inningsCounter = 0;
                double controlvlCounter = 0, controlvrCounter = 0;
                double stuffvlCounter = 0, stuffvrCounter = 0;
                double phrvlCounter = 0, phrvrCounter = 0;
                double pbabipvlCounter = 0, pbabipvrCounter = 0;
    
                double paCounter = 0;
                double eyevlCounter = 0, eyevrCounter = 0;
                double avkvlCounter = 0, avkvrCounter = 0;
                double powervlCounter = 0, powervrCounter = 0;
                double babipvlCounter = 0, babipvrCounter = 0;
    
                for ( CardTournamentResult result : results) {
                    Card card = cardsRepo.getCard(result.getCid());
                    double ip = result.getInnings();
                    double pa = result.getPa().doubleValue();
                    if(ip > 0){
                        inningsCounter = inningsCounter + ip;
                        controlvlCounter = controlvlCounter + ip * card.getControlvl();
                        controlvrCounter = controlvrCounter + ip * card.getControlvr();
                        stuffvlCounter = stuffvlCounter + ip * card.getStuffvl();
                        stuffvrCounter = stuffvrCounter + ip * card.getStuffvr();
                        phrvlCounter = phrvlCounter + ip * card.getPhrvl();
                        phrvrCounter = phrvrCounter + ip * card.getPhrvr();
                        pbabipvlCounter = pbabipvlCounter + ip * card.getPbabipvl();
                        pbabipvrCounter = pbabipvrCounter + ip * card.getPbabipvr();
                    }
                    if(pa > 0){
                        paCounter = paCounter + pa;
                        eyevlCounter = eyevlCounter + pa * card.getEyevL();
                        eyevrCounter = eyevrCounter + pa * card.getEyevR();
                        avkvlCounter = avkvlCounter + pa * card.getKsvL();
                        avkvrCounter = avkvrCounter + pa * card.getKsvR();
                        powervlCounter = powervlCounter + pa * card.getPowervL();
                        powervrCounter = powervrCounter + pa * card.getPowervR();
                        babipvlCounter = babipvlCounter + pa * card.getBABIPvL();
                        babipvrCounter = babipvrCounter + pa * card.getBABIPvR();
                    }
                }
                CardStatSet averagesvr = new CardStatSet(), averagesvl = new CardStatSet();
                averagesvl.setControl(controlvlCounter/inningsCounter);
                averagesvl.setPbabip(pbabipvlCounter/inningsCounter);
                averagesvl.setPhr(phrvlCounter/inningsCounter);
                averagesvl.setStuff(stuffvlCounter/inningsCounter);
                averagesvl.setEye(eyevlCounter/paCounter);
                averagesvl.setBabip(babipvlCounter/paCounter);
                averagesvl.setPower(powervlCounter/paCounter);
                averagesvl.setAvk(avkvlCounter/paCounter);
                metaCard.setAvgVsLeft(averagesvl);
    
                averagesvr.setPhr(phrvrCounter/inningsCounter);
                averagesvr.setControl(controlvrCounter/inningsCounter);
                averagesvr.setPbabip(pbabipvrCounter/inningsCounter);
                averagesvr.setStuff(stuffvrCounter/inningsCounter);
                averagesvr.setEye(eyevrCounter/paCounter);
                averagesvr.setBabip(babipvrCounter/paCounter);
                averagesvr.setPower(powervrCounter/paCounter);
                averagesvr.setAvk(avkvrCounter/paCounter);
                metaCard.setAvgVsRight(averagesvr);
    
        }
        return this.cardStatSets[h.ordinal()][a.ordinal()];
    }
*/
    @Getter
    @Setter
    CardStatSet avgVsLeft;

    @Getter
    @Setter
    CardStatSet avgVsRight;

    @Getter
    @Setter
    CardStatSet minVsLeft;

    @Getter
    @Setter
    CardStatSet minVsRight;

    @Getter
    @Setter
    CardStatSet maxVsLeft;

    @Getter
    @Setter
    CardStatSet maxVsRight;

}
