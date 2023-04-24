package com.track.yourself.models.dto;

import lombok.Data;

import java.util.Date;


@Data
public class FindCollectionItemsRequest {
    private String description;
    private String status;
}
