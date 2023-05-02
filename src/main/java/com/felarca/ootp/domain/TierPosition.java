package com.felarca.ootp.domain;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor

public class TierPosition {
    @Setter
	@Getter
	private long tier;
    @Setter
	@Getter
	private long position;
    @Setter
	@Getter
	private long count;
}
