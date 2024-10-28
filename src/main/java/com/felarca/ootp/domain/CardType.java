package com.felarca.ootp.domain;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public final class CardType {

    @Setter
    @Getter
    int cardType;
    @Setter
    @Getter
    int cardSubType;
    @Setter
    @Getter
    String cardDisplay;
    // Date?

    private static final ArrayList<CardType> types = new ArrayList<CardType>() {
        {
            add(new CardType(1, 0, "Live"));
            add(new CardType(2, 0, "Negro League"));
            add(new CardType(2, 10, "Negro League 2"));
            add(new CardType(3, 0, "Rookie Sensation"));
            add(new CardType(4, 0, "Legend"));
            add(new CardType(5, 0, "All-Star"));
            add(new CardType(6, 0, "Future Legend"));
            add(new CardType(6, 10, "Future Legend 2"));
            add(new CardType(7, 0, "Snapshot"));
            add(new CardType(8, 0, "Unsung Hero"));
            add(new CardType(9, 0, "Hardware Heroes"));
            add(new CardType(10, 10, "Mission Edition"));
            add(new CardType(10, 16, "PTCS"));
            add(new CardType(10, 17, "Limited Edition 1"));
            add(new CardType(10, 18, "10 18"));
            add(new CardType(10, 23, "Limited Edition 2"));
            add(new CardType(10, 29, "Tournament Edition 1"));
            add(new CardType(10, 32, "Baseball Reference 1"));
            add(new CardType(10, 33, "Baseball Reference 2"));
            add(new CardType(10, 34, "Baseball Reference 3"));
            add(new CardType(10, 35, "Baseball Reference 4"));
            add(new CardType(10, 39, "Limited Edition 4"));
            add(new CardType(10, 40, "Exclusive"));
            add(new CardType(10, 41, "PT Elite 1"));
            add(new CardType(10, 42, "PT Elite 2"));
            add(new CardType(10, 43, "Tournament Edition 2"));
            add(new CardType(10, 45, "Throwback Series"));
            add(new CardType(10, 46, "Throwback Series, 2012"));
            add(new CardType(10, 47, "Throwback Series, 47"));
            add(new CardType(10, 48, "Throwback Series, 48"));
            add(new CardType(10, 49, "Throwback Series, 49"));
            add(new CardType(10, 50, "Throwback Series, 50"));
            add(new CardType(10, 51, "Throwback Series, 51"));
        }
    };

    public static String toString(long cardType, long subType) {
        for (CardType type : types) {
            if (type.cardType == cardType && type.cardSubType == subType)
                return type.cardDisplay;
        }
        return cardType + ":" + subType;
    }

    public static CardType getCardType(long cardType, long subType) {
        for (CardType type : types) {
            if (type.cardType == cardType && type.cardSubType == subType)
                return type;
        }
        return null;
    }

    public static CardType getCardType(String display) {
        for (CardType type : types) {
            if (type.cardDisplay == display)
                return type;
        }
        return null;
    }

    public static ArrayList<CardType> getTypes(){
        return types;
    }
}
