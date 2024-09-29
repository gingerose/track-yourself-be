package com.track.yourself.repository;

import com.track.yourself.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

  @Query("""
            SELECT c.title, n.noteId, n.userId, n.title, n.date, n.text, c.categoryId FROM Note AS n
            JOIN Category c on c.categoryId = n.categoryId
            WHERE n.userId = ?1 \s
            AND n.date BETWEEN ?2 AND ?3 \s
            AND LOWER(n.title) LIKE LOWER(?4)
            AND LOWER(c.title) LIKE LOWER(?5)\s
            ORDER BY c.title, n.title""")
  List<Object[]> findLimited(Integer userId,
                             Date firstDate,
                             Date secondDate,
                             String title,
                             String category);
}
