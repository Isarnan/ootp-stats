package com.felarca.ootp.domain.results;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class RawPoint {
    @Getter
    @Setter
    private Long cardStat;

    @Getter
    @Setter
    private BigInteger observedStat;

    @Getter
    @Setter
    private BigInteger sampleSize;

    public Double getObservedStat100(){
        return (this.observedStat.doubleValue() / this.sampleSize.doubleValue()) * 100.0;
    }

    public Double getObservedStat(){
        return (this.observedStat.doubleValue() / this.sampleSize.doubleValue());
    }

}
