package com.track.yourself.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@Data
public class PlansStatistic {
    private String status;
    private Long amount;

    public PlansStatistic(String status, Long count) {
        this.status = status;
        this.amount = count;
    }
}
