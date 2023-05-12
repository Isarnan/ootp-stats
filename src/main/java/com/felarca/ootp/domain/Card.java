package com.felarca.ootp.domain;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
@AllArgsConstructor
public class Card {    	
	@Getter
	@Setter
	private long cid;

    @Getter
	@Setter
	private String fn;
    @Getter
	@Setter
	private String ln;

    @Getter
	@Setter
	private long stamina;

    @Getter
	@Setter
	private long stuffvl;
    @Getter
	@Setter
	private long  controlvl;
    @Getter
	@Setter
	private long phrvl;
    @Getter
	@Setter
	private long pbabipvl;
    @Getter
	@Setter
	private long  stuffvr;
    @Getter
	@Setter
	private long controlvr;
    @Getter
	@Setter
	private long phrvr;
    @Getter
	@Setter
	private long pbabipvr;
	@Setter
	private long handed;
	@Getter
	@Setter
	private long ifr;
	@Getter
	@Setter
	private long ofRange;
	@Getter
	@Setter
	private long ratingC;
	@Getter
	@Setter
	private long rating1B;
	@Getter
	@Setter
	private long rating2B;
	@Getter
	@Setter
	private long rating3B;
	@Getter
	@Setter
	private long ratingSS;
	@Getter
	@Setter
	private long ratingRF;
	@Getter
	@Setter
	private long ratingCF;
	@Getter
	@Setter
	private long ratingLF;
	@Getter
	@Setter
	private long ContactvL;
	@Getter
	@Setter
	private long GapvL;
	@Getter
	@Setter
	private long PowervL;
	@Getter
	@Setter
	private long EyevL;
	@Getter
	@Setter
	private long KsvL;
	@Getter
	@Setter
	private long BABIPvL;
	@Getter
	@Setter
	private long ContactvR;
	@Getter
	@Setter
	private long GapvR;
	@Getter
	@Setter
	private long PowervR;
	@Getter
	@Setter
	private long EyevR;
	@Getter
	@Setter
	private long KsvR;
	@Getter
	@Setter
	private long BABIPvR;
	@Getter
	@Setter
	private long overall;


	// End of DB Columns
	public Integer getVra(){
		return (int)KsvR + (int)BABIPvR;
	}
	
	public Integer getVrp(){
		return (int)EyevR + (int)PowervR;
	}

	public Integer getVla(){
		return (int)KsvL + (int)BABIPvL;
	}

	public Integer getVlp(){
		return (int)EyevL + (int)PowervL;
	}

    public String getHanded(){
        switch((int)this.handed){
            case 1:
                return "RHP";
            case 2:
                return "LHP";
            default:
                return "WTF";

        }
    }

    public long getSplits(){
        return (stuffvl + controlvl + phrvl + pbabipvl) - (stuffvr + controlvr + phrvr + pbabipvr);
    }

}
