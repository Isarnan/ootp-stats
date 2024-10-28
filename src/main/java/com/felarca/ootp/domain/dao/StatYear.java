package com.felarca.ootp.domain.dao;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;

@Data
public class StatYear {
    //    Year,Tms,Bat,BatAge,R/G,G,PA,AB,R,H,1B,2B,3B,HR,RBI,SB,CS,BB,SO,BA,OBP,SLG,OPS,TB,GDP,HBP,SH,SF,IBB,BIP

    @CsvBindByPosition(position = 0)
    private String year;

    @CsvBindByPosition(position = 1)
    private String tms;

    @CsvBindByPosition(position = 2)
    private Integer bat;

    @CsvBindByPosition(position = 3)
    private double batAge;

    @CsvBindByPosition(position = 4)
    private double runsPerGame;

    @CsvBindByPosition(position = 5)
    private Integer games;

    @CsvBindByPosition(position = 6)
    private double plateAppearances;

    @CsvBindByPosition(position = 7)
    private double atBats;

    @CsvBindByPosition(position = 8)
    private double runs;

    @CsvBindByPosition(position = 9)
    private double hits;

    @CsvBindByPosition(position = 10)
    private double singles;

    @CsvBindByPosition(position = 11)
    private double doubles;

    @CsvBindByPosition(position = 12)
    private double triples;

    @CsvBindByPosition(position = 13)
    private double homeruns;

    @CsvBindByPosition(position = 14)
    private double rbi;

    @CsvBindByPosition(position = 15)
    private double stolenBases;

    @CsvBindByPosition(position = 16)
    private double caughtStealing;

    @CsvBindByPosition(position = 17)
    private double walks;

    @CsvBindByPosition(position = 18)
    private double strikeOuts;

    @CsvBindByPosition(position = 19)
    private double battingAverage;

    @CsvBindByPosition(position = 20)
    private double onBasePercentage;

    @CsvBindByPosition(position = 21)
    private double sluggingPercentage;

    @CsvBindByPosition(position = 22)
    private double ops;

    @CsvBindByPosition(position = 23)
    private double totalBases;

    @CsvBindByPosition(position = 24)
    private double gdp;

    @CsvBindByPosition(position = 25)
    private double hbp;

    @CsvBindByPosition(position = 26)
    private double sh;

    @CsvBindByPosition(position = 27)
    private double sf;

    @CsvBindByPosition(position = 28)
    private double ibb;

    @CsvBindByPosition(position = 29)
    private double bip;

    public double getBabip(){
        return (singles + doubles + triples) / bip;
    }

}