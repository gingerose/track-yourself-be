package com.track.yourself.repository;

import com.track.yourself.models.Collection;
import com.track.yourself.models.dto.CollectionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    @Query("""
            SELECT c.collectionId, c.title, COUNT(ci.itemId) as total_count, SUM(CASE WHEN ci.status = 'DONE' THEN 1 ELSE 0 END)\s
            FROM Collection AS c 
            LEFT JOIN CollectionItem AS ci ON ci.collectionId = c.collectionId\s
            WHERE c.userId = ?1 
            AND c.date BETWEEN ?2 AND ?3 
            AND LOWER(c.title) LIKE LOWER(?4) 
            GROUP BY c.collectionId, c.title
            ORDER BY c.title""")
    List<Object[]> findLimited(Integer userId,
                                    Date firstDate,
                                    Date secondDate,
                                    String title);
}
