package com.track.yourself.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "collection_items")
@AllArgsConstructor
public class CollectionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;
    @Column(name = "collection_id")
    @JoinColumn(name = "collection_id", referencedColumnName = "collection_id")
    private Integer collectionId;
    private String status;
    private String description;
}
