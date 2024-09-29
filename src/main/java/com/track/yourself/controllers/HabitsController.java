package com.track.yourself.controllers;

import com.track.yourself.models.Habit;
import com.track.yourself.models.HabitItem;
import com.track.yourself.models.dto.FindHabitsRequest;
import com.track.yourself.repository.HabitItemRepository;
import com.track.yourself.repository.HabitRepository;
import com.track.yourself.repository.UserRepository;
import com.track.yourself.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/users/habits")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HabitsController {
    @Autowired
    private HabitRepository habitRepository;
    @Autowired
    private HabitItemRepository habitItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HabitService habitService;

    @PostMapping
    public ResponseEntity<?> createHabit(@RequestBody Habit habit) {
        if (userRepository.findById(habit.getUserId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        if (habit.getDate() == null) {
            habit.setDate(new Date());
        }
        return ResponseEntity.ok(habitRepository.save(habit));
    }

    @PutMapping
    public ResponseEntity<?> updateHabit(@RequestBody Habit habit) {
        if(habitRepository.findById(habit.getHabitId()).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habit not found!");
        }
        habit.setDate(habitRepository.findById(habit.getHabitId()).get().getDate());
        return ResponseEntity.ok(habitRepository.save(habit));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCollection(@RequestParam Integer habitId) {
        if(habitRepository.findById(habitId).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habit not found!");
        }
        habitRepository.deleteById(habitId);
        return ResponseEntity.ok("Habit was deleted");
    }

    @PostMapping("/search")
    public ResponseEntity<?> getHabits(@RequestBody FindHabitsRequest findHabitsRequest) {
        if (userRepository.findById(findHabitsRequest.getUserId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        return ResponseEntity.ok(habitService.searchHabitsByParams(findHabitsRequest));
    }

    @PostMapping("/{habitId}/items")
    public ResponseEntity<?> createHabitItem(@RequestBody HabitItem habitItem, @PathVariable int habitId) {
        if (habitRepository.findById(habitId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habit not found!");
        }

        return ResponseEntity.ok(habitItemRepository.save(habitItem));
    }

    @PutMapping("/{habitId}/items")
    public ResponseEntity<?> updateHabitItem(@RequestBody HabitItem habitItem, @PathVariable int habitId) {
        if(habitRepository.findById(habitId).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habit not found!");
        }
        return ResponseEntity.ok(habitItemRepository.save(habitItem));
    }

  @DeleteMapping("/{habitId}/items")
  public ResponseEntity<?> deleteHabitItem(@RequestParam Integer itemId, @PathVariable int habitId) {
    if(habitRepository.findById(habitId).isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habit not found!");
    }
    habitItemRepository.deleteById(itemId);
    return ResponseEntity.ok("Deleted");
  }
}
