package com.track.yourself.models.dto;

import lombok.Data;

import java.beans.ConstructorProperties;


@Data
public class CollectionsStatistic {
    private String title;
    private long amountOfDone;
    private long fullAmount;

    public CollectionsStatistic(String title, long amountOfDone, long fullAmount) {
        this.title = title;
        this.amountOfDone = amountOfDone;
        this.fullAmount = fullAmount;
    }

    @ConstructorProperties({"title", "amountOfDone", "amountOfEmpty"})
    public CollectionsStatistic() {
    }
}
