package com.track.yourself.models.dto;

import lombok.Data;

import java.util.Date;


@Data
public class FindPlansRequest {
    private Integer userId;
    private Date date;
    private String description;
    private String status;
}
