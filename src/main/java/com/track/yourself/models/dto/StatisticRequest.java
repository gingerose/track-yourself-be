package com.track.yourself.models.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;


@Data
@ToString
public class StatisticRequest {
    private LocalDate date;
    private Integer userId;
    private boolean year;

    public boolean isYear() {
        return year;
    }
}
