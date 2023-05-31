package com.track.yourself.repository;

import com.track.yourself.models.Collection;
import com.track.yourself.models.dto.CollectionsStatistic;
import com.track.yourself.models.dto.PlansStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionsStatisticRepository extends JpaRepository<Collection, Integer> {
    @Query("""
            SELECT new com.track.yourself.models.dto.CollectionsStatistic(c.title,
                SUM(CASE WHEN ci.status = 'DONE' THEN 1 ELSE 0 END),
                COUNT(ci))
            FROM Collection c
            LEFT JOIN CollectionItem as ci on ci.collectionId = c.collectionId
            WHERE c.userId = ?1
                AND EXTRACT(YEAR FROM c.date) = ?2
            GROUP BY c.title
            """)
    List<CollectionsStatistic> findByYear(Integer userId,
                                          Integer year);

    @Query("""
             SELECT new com.track.yourself.models.dto.CollectionsStatistic(c.title,
                SUM(CASE WHEN ci.status = 'DONE' THEN 1 ELSE 0 END),
                COUNT(ci))
            FROM Collection c
            LEFT JOIN CollectionItem as ci on ci.collectionId = c.collectionId
            WHERE c.userId = ?1
              AND EXTRACT(MONTH FROM c.date) = ?2
            GROUP BY c.title""")
    List<CollectionsStatistic> findByMonth(Integer userId,
                                           Integer month);
}
