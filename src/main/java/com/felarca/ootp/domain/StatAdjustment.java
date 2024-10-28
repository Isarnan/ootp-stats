package com.felarca.ootp.domain;

import com.felarca.ootp.domain.dao.StatYear;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class StatAdjustment {

    @Getter
    @Setter
    private StatYear modelYear;

    @Getter
    @Setter
    private StatYear tournamentYear;

    @Getter
    @Setter
    private Ballpark modelBallpark;

    @Getter
    @Setter
    private Ballpark tournamentBallpark;

    public double getHomerunAdjustment(){        
        return tournamentYear.getHomeruns() / modelYear.getHomeruns();
    }

    public double getWalkAdjustment(){        
        return tournamentYear.getWalks() / modelYear.getWalks();
    }

    public double getStrikeoutAdjustment(){        
        return tournamentYear.getStrikeOuts() / modelYear.getStrikeOuts();
    }

    public double getBabipAdjustment(){        
        return tournamentYear.getBabip() / modelYear.getBabip();
    }

    public double getAvgLHB(){
        return tournamentBallpark.getAvgLHB() / modelBallpark.getAvgLHB();
    }

    public double getAvgRHB(){
        return tournamentBallpark.getAvgRHB() / modelBallpark.getAvgRHB();

    }

    public double getHrLHB(){
        return tournamentBallpark.getHrLHB() / modelBallpark.getHrLHB();

    }

    public double getHrRHB(){
        return tournamentBallpark.getHrRHB() / modelBallpark.getHrRHB();

    }
    
}
