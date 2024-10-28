package com.felarca.ootp.domain;

import lombok.Getter;
import lombok.Setter;

public class DataSet {
    @Getter
    @Setter
    private String dsShortName;

    public DataSet(String dataSet) {
        this.dsShortName = dataSet;
    }

    @Getter
    @Setter
    private Integer starterPitcherRecords = 0;

    @Getter
    @Setter
    private Integer reliefPitcherRecords = 0;

    @Getter
    @Setter
    private Integer mixedPitcherRecords = 0;
}
