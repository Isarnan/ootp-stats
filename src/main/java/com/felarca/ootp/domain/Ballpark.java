package com.felarca.ootp.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Ballpark {

    private static List<Ballpark> ballparks = new ArrayList<Ballpark>();
    
    @Setter
    @Getter
    private String ballpark;

    @Setter
    @Getter
    private String team;

    @Setter
    @Getter
    private String year;

    @Setter
    @Getter
    private double avgLHB;

    @Setter
    @Getter
    private double avgRHB;

    @Setter
    @Getter
    private double hrLHB;

    @Setter
    @Getter
    private double hrRHB;

    @Setter
    @Getter
    private double doubles;

    @Setter
    @Getter
    private double triples;

    static {
        ballparks.add(new Ballpark("Heinsohn Ballpark", "None", "2023", 1.0, 1.0, 1.0, 1.0, 1.0, 1.0));
        ballparks.add(new Ballpark("Shibe Park AL", "None", "1920", 1.003, 0.990, 1.153, 1.200, 0.975, 0.895));
        ballparks.add(new Ballpark("Colorado Field", "None", "2023", 1.126, 1.117, 1.133, 1.128,1.143, 1.256));
        ballparks.add(new Ballpark("Seals Stadium", "None", "1959", 0.986, 0.959, 0.960, 0.990, .928, 0.881));
        ballparks.add(new Ballpark("Royals Stadium", "None", "1978", 1.054, 0.998, 0.881, 0.930, 1.086, 1.160));
        ballparks.add(new Ballpark("Citizens Bank Park", "None", "2023", 1.000, 0.991, 1.128, 1.142, 0.998, 0.997));
        ballparks.add(new Ballpark("Hanbat Baseball Stadium", "None", "2023", 0.971, 0.970, 1.121, 1.123, 0.947, 0.866));
        ballparks.add(new Ballpark("Sajik Baseball Stadium", "None", "2023", 1.067, 1.068, 0.951, 0.955, 1.094, 0.878));
        ballparks.add(new Ballpark("Wrigley Field", "None", "1985", 1.039, 1.018, 1.187, 1.144, 0.958, 1.025));
        ballparks.add(new Ballpark("LA Coliseum", "None", "1959", 0.988, 1.010, 0.986, 1.163, 0.927, 1.048));
        ballparks.add(new Ballpark("Candlestick Park", "None", "1962", 0.933, 0.976, 1.125, 0.973, 0.958, 0.894));
        ballparks.add(new Ballpark("Polo Grounds", "None", "1939", 0.952, 0.972, 1.201, 1.202, 0.872, 1.096));
    }
    
    
    public Ballpark(String ballpark, String team, String year, double avgLHB, double avgRHB, double hrLHB, double hrRHB, double doubles, double triples){
        this.ballpark = ballpark;
        this.team = team;
        this.year = year;
        this.avgLHB = avgLHB;
        this.avgRHB = avgRHB;
        this.hrLHB = hrLHB;
        this.hrRHB = hrRHB;
        this.doubles = doubles;
        this.triples = triples;

    }

    static public Ballpark findBallparkByName(String bp){
        Ballpark ballpark = new Ballpark("Heinsohn Ballpark", "None", "2023", 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
		for( Ballpark x : Ballpark.ballparks){
			if( x.getBallpark().equals(bp)){
				ballpark = x;
				break;
			}
		}
		return ballpark;
    }
}
