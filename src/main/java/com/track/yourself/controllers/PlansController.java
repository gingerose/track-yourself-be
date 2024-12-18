package com.track.yourself.controllers;

import com.track.yourself.models.Plan;
import com.track.yourself.models.dto.FindPlansRequest;
import com.track.yourself.repository.PlanRepository;
import com.track.yourself.repository.UserRepository;
import com.track.yourself.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/users/plans")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PlansController {
  @Autowired
  private PlanRepository planRepository;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PlanService planService;

  @PostMapping
  public ResponseEntity<?> createPlan(@RequestBody Plan plan) {
    if (userRepository.findById(plan.getUserId()).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
    }

    if (plan.getCreationDate() == null) {
      plan.setCreationDate(new Date());

      LocalDate currentDate = LocalDate.now();
      DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
      int dayOfWeekValue = dayOfWeek.getValue();
      plan.setDayOfWeek(dayOfWeekValue);
    }

    if (plan.getDayOfWeek() == null) {
      LocalDate currentDate = LocalDate.now();

      DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

      int dayOfWeekValue = dayOfWeek.getValue();
      plan.setDayOfWeek(dayOfWeekValue);
    }

    return ResponseEntity.ok(planRepository.save(plan));
  }

  @PutMapping
  public ResponseEntity<?> updatePlan(@RequestBody Plan plan) {
    if (planRepository.findById(plan.getPlanId()).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found!");
    }
    System.out.println(plan);
    return ResponseEntity.ok(planRepository.save(plan));
  }

  @GetMapping
  public ResponseEntity<?> getPlanById(@RequestParam Integer planId) {
    return ResponseEntity.ok(planRepository.findById(planId));
  }

  @DeleteMapping
  public ResponseEntity<?> deletePlan(@RequestParam Integer planId) {
    if (planRepository.findById(planId).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found!");
    }
    planRepository.deleteById(planId);
    return ResponseEntity.ok("Plan was deleted");
  }

  @PostMapping("/search")
  public ResponseEntity<?> getPlans(@RequestBody FindPlansRequest plansRequest) {
    if (userRepository.findById(plansRequest.getUserId()).isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
    }

    return ResponseEntity.ok(planService.searchPlansByParams(plansRequest));
  }
}
