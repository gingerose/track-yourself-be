package com.track.yourself.repository;

import com.track.yourself.models.Category;
import com.track.yourself.models.Habit;
import com.track.yourself.models.Plan;
import com.track.yourself.models.dto.PlansStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlansStatisticRepository extends JpaRepository<Plan, Integer> {
  @Query("""
    SELECT new com.track.yourself.models.dto.PlansStatistic(p.status, COUNT(p.status))
    FROM Plan AS p
    WHERE p.userId = ?1
    AND EXTRACT(YEAR FROM p.creationDate) = ?2
    GROUP BY p.status""")
  List<PlansStatistic> findByYear(Integer userId, Integer year);

  @Query("""
          SELECT new com.track.yourself.models.dto.PlansStatistic(p.status, COUNT(p.status))
                 FROM Plan AS p
                 WHERE p.userId = ?1
                 AND EXTRACT(month FROM p.creationDate) = ?2
                 GROUP BY p.status""")
  List<PlansStatistic> findByMonth(Integer userId,
                                  Integer month);
}
