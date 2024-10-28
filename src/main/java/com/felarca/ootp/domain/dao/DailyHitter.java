package com.felarca.ootp.domain.dao;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;

@Data
public class DailyHitter {
    //    "VBR","Player","Team","Positions","Opp","AB","R","HR","RBI","SB","AVG","OBP","H","2B","3B","BB","SO","Rost%"


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
    private double ab;

    @CsvBindByPosition(position = 6)
    private double runs;

    @CsvBindByPosition(position = 7)
    private double homeruns;

    @CsvBindByPosition(position = 8)
    private double rbi;

    @CsvBindByPosition(position = 9)
    private double sb;

    @CsvBindByPosition(position = 10)
    private double avg;

    @CsvBindByPosition(position = 11)
    private double obp;

    @CsvBindByPosition(position = 12)
    private double h;

    @CsvBindByPosition(position = 13)
    private double doubles;

    @CsvBindByPosition(position = 14)
    private double triples;

    @CsvBindByPosition(position = 15)
    private double walks;

    @CsvBindByPosition(position = 16)
    private double strikeouts;

    @CsvBindByPosition(position = 17)
    private String rostered;

    private long ootpPosition = -99;
    private long overall = -1;

    public double getPoints(){
        double points = 0;
        points += ((h - doubles - triples - homeruns) *  4);
        points += (doubles * 6);
        points += (triples * 10);
        points += (homeruns * 15);
        points += (runs * 6);
        points += (rbi * 6);
        points += (walks * 3);
        points += (sb * 8);
        if(homeruns > 3)points += 100;
        if(h > 4)points += ((h - 4) * 21);
        return points;
    }

}