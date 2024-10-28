
package com.felarca.ootp.domain;

import lombok.Getter;
import lombok.Setter;

public class CardStatSet {
    @Getter
    @Setter
    private double power;

    @Getter
    @Setter
    private double eye;

    @Getter
    @Setter
    private double avk;

    @Getter
    @Setter
    private double babip;

    @Getter
    @Setter
    private double control;

    @Getter
    @Setter
    private double stuff;

    @Getter
    @Setter
    private double pbabip;

    @Getter
    @Setter
    private double phr;

    public enum Handed {
        LEFT, RIGHT, SWITCH;

        public static final int size;
        static {
            size = values().length;
        }
    }

    public enum Aggregate {
        MAX, MIN, AVG;

        public static final int size;
        static {
            size = values().length;
        }
    }

    public enum Stat {
        POWER, EYE, AVK, BABIP, CONTROL, STUFF, PBABIP, PHR;

        public static final int size;
        static {
            size = values().length;
        }
    }

    public enum DefensiveStat {
        CATCHERBLOCKING, CATCHERFRAMING, CATCHERARM, INFIELDRANGE, INFIELDERROR, INFIELDARM, TURNDOUBLEPLAY, OUTFIELDRANGE, OUTFIELDERROR, OUTFIELDARM;

        public static final int size;
        static {
            size = values().length;
        }
    }

    public enum Position {
        UNUSED, PITCHER, CATCHER, FIRSTBASE, SECONDBASE, THIRDBASE, SHORTSTOP, LEFTFIELD, CENTERFIELD, RIGHTFIELD, DESIGNATEDHITTER, STARTINGPITCHER, RELIEFPITCHER;

        public static final int size;
        static {
            size = values().length;
        }
    }

    public double getStat(CardStatSet.Stat stat){
        switch(stat){
            case AVK:
                return avk;
            case BABIP:
                return babip;
            case CONTROL:
                return control;
            case EYE:
                return eye;
            case PBABIP:
                return pbabip;
            case PHR:
                return phr;
            case POWER:
                return power;
            case STUFF:
                return stuff;
            default:
                return -1.0;

        }
    }

}


