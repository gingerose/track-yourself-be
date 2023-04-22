package com.track.yourself.repository;

import com.track.yourself.models.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {
    @Query("""
            SELECT p.planId, p.user_id, p.description, p.status, p.date \s
            FROM Plan AS p\s
            WHERE p.user_id = ?1 \s
            AND p.date BETWEEN ?2 AND ?3 \s
            AND LOWER(p.description) LIKE LOWER(?4) \s
            AND p.status LIKE ?5""")
    List<Object[]> searchPlansByParamas(Integer userId, Date startOfWeek, Date endOfWeek, String description, String status);
}