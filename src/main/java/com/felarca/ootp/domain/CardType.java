package com.felarca.ootp.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

public final class CardType {

    @AllArgsConstructor
    private static class Type {
        int cardType;
        int cardSubType;
        String cardDisplay;
        // Date?
    }

    private static final List<Type> types = new ArrayList<Type>() {
        {
            add(new Type(1, 0, "Live"));
            add(new Type(2, 0, "Negro League"));
            add(new Type(3, 0, "Rookie Sensation"));
            add(new Type(4, 0, "Legend"));
            add(new Type(5, 0, "All-Star"));
            add(new Type(6, 0, "Future Legend"));
            add(new Type(7, 0, "Snapshot"));
            add(new Type(8, 0, "Unsung Hero"));
            add(new Type(9, 0, "Hardware Heroes"));
            add(new Type(10, 10, "Mission Edition"));
            add(new Type(10, 16, "PTCS"));
            add(new Type(10, 17, "Limited Edition 1"));
            add(new Type(10, 19, "Build-a-Lineup"));
            add(new Type(10, 23, "Limited Edition 2"));
            add(new Type(10, 28, "Special Edition"));
            add(new Type(10, 29, "Tournament Edition 1"));
            add(new Type(10, 30, "Topps"));
            add(new Type(10, 31, "Immortal Seasons"));
            add(new Type(10, 32, "Baseball Reference 1"));
            add(new Type(10, 33, "Baseball Reference 2"));
            add(new Type(10, 34, "Baseball Reference 3"));
            add(new Type(10, 35, "Baseball Reference 4"));
            add(new Type(10, 38, "Limited Edition 3"));
            add(new Type(10, 39, "Limited Edition 4"));
            add(new Type(10, 40, "Exclusive"));
            add(new Type(10, 41, "PT Elite 1"));
            add(new Type(10, 42, "PT Elite 2"));
            add(new Type(10, 43, "Tournament Edition 2"));
        }
    };

    public static String getCardType(long cardType, long subType) {
        for (Type type : types) {
            if (type.cardType == cardType && type.cardSubType == subType)
                return type.cardDisplay;
        }
        return cardType + ":" + subType;
    }

}
