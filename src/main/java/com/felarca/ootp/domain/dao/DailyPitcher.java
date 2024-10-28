package com.felarca.ootp.domain.dao;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;

@Data
public class DailyPitcher {
    // "VBR","Player","Team","Positions","Opp","IP","K","W","SV","ERA","WHIP","ER","H","BB","HR","G","GS","L","CG","Rost%"

    @CsvBindByPosition(position = 0)
    private String br;

    @CsvBindByPosition(position = 1)
    private String name;

    @CsvBindByPosition(position = 2)
    private String team;

    @CsvBindByPosition(position = 3)
    private String Positions;

    @CsvBindByPosition(position = 4)
    private String opponent;

    @CsvBindByPosition(position = 5)
    private double ip;

    @CsvBindByPosition(position = 6)
    private double k;

    @CsvBindByPosition(position = 7)
    private double w;

    @CsvBindByPosition(position = 8)
    private double sv;

    @CsvBindByPosition(position = 9)
    private double era;

    @CsvBindByPosition(position = 10)
    private double whip;

    @CsvBindByPosition(position = 11)
    private double er;

    @CsvBindByPosition(position = 12)
    private double h;

    @CsvBindByPosition(position = 13)
    private double walks;

    @CsvBindByPosition(position = 14)
    private double homeruns;

    @CsvBindByPosition(position = 15)
    private double g;

    @CsvBindByPosition(position = 16)
    private double gs;

    @CsvBindByPosition(position = 16)
    private double l;

    @CsvBindByPosition(position = 16)
    private double cg;

    @CsvBindByPosition(position = 17)
    private String rostered;

    private long ootpPosition = -99;
    private long overall = -1;
    private long pitcherRole = 11;

    public double getPoints() {
        double points = 0;
        if (pitcherRole == 11) {
            // Starter
            points += (w * 20);
            points += (cg * 50);
            points += (ip * 4);
            points += (k * 2);
            points -= (er * 2);
            if (k > 10)
                points += 50;
            if(ip >= 9 && er == 0){
                points += 100;
            }
        } else {
            // Reliever(RP or CL)
            points += (w * 10);
            points += (sv * 30);
            points += (h * 15);
            points += (ip * 4);
            points -= (k * 3);
            points -= (er * 2);
        }

        return points;
    }

}