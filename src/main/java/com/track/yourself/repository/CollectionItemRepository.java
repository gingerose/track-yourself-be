package com.track.yourself.repository;

import com.track.yourself.models.CollectionItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CollectionItemRepository extends JpaRepository<CollectionItem, Integer> {
    @Query("""
            SELECT itemId, collectionId, description, status
            FROM CollectionItem
            WHERE collectionId = ?1
            AND LOWER(description) LIKE LOWER(?2)
            AND LOWER(status) LIKE LOWER(?3)""")
    List<Object[]> findLimited(Integer collectionId,
                               String description,
                               String status);
}
