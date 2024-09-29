package com.track.yourself.controllers;

import com.track.yourself.models.Category;
import com.track.yourself.models.Note;
import com.track.yourself.models.Plan;
import com.track.yourself.models.dto.FindNotesRequest;
import com.track.yourself.models.dto.FindPlansRequest;
import com.track.yourself.repository.CategoryRepository;
import com.track.yourself.repository.NoteRepository;
import com.track.yourself.repository.UserRepository;
import com.track.yourself.service.NoteService;
import com.track.yourself.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;

@RestController
@RequestMapping("/api/users/notes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotesController {
  @Autowired
  private NoteRepository noteRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private NoteService noteService;

  @PostMapping("/categories")
  public ResponseEntity<?> createCategory(@RequestBody Category category) {
    if (categoryRepository.findByTitle(category.getTitle()).isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category exist!");
    } else
      return ResponseEntity.ok(categoryRepository.save(category));
  }

  @PutMapping("/categories")
  public ResponseEntity<?> updateCategory(@RequestBody Category category) {
    if (categoryRepository.findById(category.getCategoryId()).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found!");
    }
    return ResponseEntity.ok(categoryRepository.save(category));
  }

  @DeleteMapping("/categories")
  public ResponseEntity<?> deleteCategory(@RequestParam Integer categoryId) {
    if (categoryRepository.findById(categoryId).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found!");
    }
    categoryRepository.deleteById(categoryId);
    return ResponseEntity.ok("Category was deleted");
  }

  @GetMapping("/categories")
  public ResponseEntity<?> getCategories(@RequestParam Integer userId) {
    return ResponseEntity.ok(categoryRepository.findByUserId(userId));
  }

  @PostMapping
  public ResponseEntity<?> createNote(@RequestBody Note note) {
    if (userRepository.findById(note.getUserId()).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
    }

    if (note.getDate() == null)
      note.setDate(new Date());

    return ResponseEntity.ok(noteRepository.save(note));
  }

  @PutMapping
  public ResponseEntity<?> updateNote(@RequestBody Note note) {
    if (noteRepository.findById(note.getNoteId()).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found!");
    }
    return ResponseEntity.ok(noteRepository.save(note));
  }

  @DeleteMapping
  public ResponseEntity<?> deleteNote(@RequestParam Integer noteId) {
    if (noteRepository.findById(noteId).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found!");
    }
    noteRepository.deleteById(noteId);
    return ResponseEntity.ok("Note was deleted");
  }

  @GetMapping("/{noteId}")
  public ResponseEntity<?> getNote(@PathVariable int noteId) {
    if (noteRepository.findById(noteId).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found!");
    }
    return ResponseEntity.ok(noteRepository.getOne(noteId));
  }

    @PostMapping("/search")
    public ResponseEntity<?> getNotes(@RequestBody FindNotesRequest notesRequest) {
        if (userRepository.findById(notesRequest.getUserId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        return ResponseEntity.ok(noteService.searchNotesByParams(notesRequest));
    }
}
