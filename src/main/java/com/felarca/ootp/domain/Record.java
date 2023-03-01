package com.felarca.ootp.domain;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Record {
	@NonNull
	@Setter
	@Getter
	private String team;
	@NonNull
	@Setter
	@Getter
	private BigInteger wins;
	@NonNull
	@Setter
	@Getter
	private BigInteger losses;
	@NonNull
	@Setter
	@Getter
	private BigInteger runsScored;
	@NonNull
	@Setter
	@Getter
	private BigInteger runsAllowed;
	@Setter
	@Getter
	private Era era;

	public Double getPct() {
		if (losses.doubleValue() != 0.0)
			return wins.doubleValue() / (losses.doubleValue() + wins.doubleValue());
		else
			return 0.0;
	}

	public Double runsAllowedPerGame() {
		return runsAllowed.doubleValue() / (wins.doubleValue() + losses.doubleValue());

	}

	public Double runsScoredPerGame() {
		return runsScored.doubleValue() / (wins.doubleValue() + losses.doubleValue());

	}
}
