package com.track.yourself.repository;

import com.track.yourself.models.HabitItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitItemRepository extends JpaRepository<HabitItem, Integer> {
}
