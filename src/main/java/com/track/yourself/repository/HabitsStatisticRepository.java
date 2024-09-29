package com.track.yourself.repository;

import com.track.yourself.models.Habit;
import com.track.yourself.models.dto.CollectionsStatistic;
import com.track.yourself.models.dto.HabitsStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitsStatisticRepository extends JpaRepository<Habit, Integer> {
    @Query("""
            SELECT new com.track.yourself.models.dto.HabitsStatistic (h.habitId,
                     h.title,
                     COUNT(hi),
                     FUNCTION('MONTH', h.date))
                                              FROM Habit AS h
                                              LEFT JOIN HabitItem AS hi ON hi.habitId = h.habitId
                                              WHERE h.userId = :userId
                                                AND FUNCTION('YEAR', h.date) = :year
                                              GROUP BY h.habitId, h.title, h.date
                                          """)
    List<HabitsStatistic> findByYear(@Param("userId") Integer userId, @Param("year") Integer year);


    @Query("""
            SELECT new com.track.yourself.models.dto.HabitsStatistic (h.habitId,
                     h.title,
                     COUNT(hi),
                     FUNCTION('MONTH', h.date))
                                              FROM Habit AS h
                                              LEFT JOIN HabitItem AS hi ON hi.habitId = h.habitId
                                              WHERE h.userId = :userId
                                                AND FUNCTION('MONTH', h.date) = :month
                                              GROUP BY h.habitId, h.title, h.date
                                          """)
    List<HabitsStatistic> findByMonth(@Param("userId") Integer userId, @Param("month") int month);

}