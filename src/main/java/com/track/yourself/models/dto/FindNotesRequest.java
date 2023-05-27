package com.track.yourself.models.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
public class FindNotesRequest {
    private Integer userId;
    private Date firstDate;
    private Date secondDate;
    private String title;
    private String category;
}
