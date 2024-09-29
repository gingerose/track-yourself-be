package com.track.yourself.models.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
public class FindCollectionItemsRequest {
    private String description;
    private String status;
}
