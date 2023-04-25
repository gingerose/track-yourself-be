package com.track.yourself.models.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
public class FindHabitsRequest {
    private Integer userId;
    private Integer year;
    private Integer month;
    private Integer page;
    private Integer amount;
}
