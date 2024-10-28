package com.felarca.ootp.domain;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Restriction {

    @Setter
    @Getter
    String shortName;

    @Setter
    @Getter
    int valueFloor;

    @Setter
    @Getter
    int valueCeiling;

    @Setter
    @Getter
    int firstYear;

    @Setter
    @Getter
    int lastYear;
    
    @Setter
    @Getter
    ArrayList<CardType> types = new ArrayList<>();
    // Date? of card release

    /*
     * TODO
     * Should I have an inner class for each card type?
     * Should it be a whitelist or a black list?
     */
    private static ArrayList<Restriction> restrictions = new ArrayList<>();

    static {
        restrictions.add(new Restriction("DiamondPlusRestriction", 90, 100, 1800, 2400, null));
    }

    public String toString() {
        return "Floor: " + valueFloor + " Ceiling: " + valueCeiling;
    }

    /*
     * This might be wrong. However, I can put the logic hear which seems
     * right....for now.
     * Could also pass values to repo...or maybe this object to repo query?
     * 
     * public String getClause(){
     * return "Overall BETWEEN " + this.valueFloor + " AND " + this.valueCeiling +
     * " AND " + "Overall BETWEEN " + this.firstYear + " AND " + this.lastYear;
     * }
     * 
     * public static ArrayList<Restrictions> restrictions = new ArrayList<>();
     * 
     * public static Restrictions getRestriction(String shortName){
     * for (Restrictions restriction : restrictions) {
     * if(restriction.shortname.equals(shortName))return restriction;
     * }
     * return null;
     * }
     */
}
