package com.track.yourself.repository;

import com.track.yourself.models.Habit;
import com.track.yourself.models.HabitItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HabitItemRepository extends JpaRepository<HabitItem, Integer> {
}
