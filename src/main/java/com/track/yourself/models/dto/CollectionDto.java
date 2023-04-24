package com.track.yourself.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CollectionDto {
    private Integer collectionId;
    private String title;
    private Long fullAmount;
    private Long doneAmount;
}
