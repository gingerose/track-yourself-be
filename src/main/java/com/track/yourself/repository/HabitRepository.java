package com.track.yourself.repository;

import com.track.yourself.models.Habit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Integer> {
  @Query("""
    SELECT h.title, h.habitId, hi.habitId, hi.itemId, hi.status, hi.date
           FROM Habit AS h
           LEFT JOIN HabitItem AS hi ON hi.habitId = h.habitId
           WHERE h.userId = ?1
           AND EXTRACT(YEAR FROM h.date) = ?2
           AND EXTRACT(MONTH FROM h.date) = ?3
           AND LOWER(h.title) LIKE LOWER(?4)""")
  List<Object[]> findLimited(Integer userId,
                             Integer year,
                             Integer month,
                             String title);
}
