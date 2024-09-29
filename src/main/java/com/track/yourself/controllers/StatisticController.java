package com.track.yourself.controllers;

import com.track.yourself.models.dto.HabitsStatistic;
import com.track.yourself.models.dto.StatisticRequest;
import com.track.yourself.repository.CollectionsStatisticRepository;
import com.track.yourself.repository.HabitsStatisticRepository;
import com.track.yourself.repository.PlansStatisticRepository;
import com.track.yourself.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users/statistic")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StatisticController {
    @Autowired
    private PlansStatisticRepository plansStatisticRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CollectionsStatisticRepository collectionsStatisticRepository;
    @Autowired
    private HabitsStatisticRepository habitsStatisticRepository;

    @PostMapping("/plans")
    public ResponseEntity<?> getPlansStatistic(@RequestBody StatisticRequest plansStatisticRequest) {
        if (userRepository.findById(plansStatisticRequest.getUserId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        if (plansStatisticRequest.isYear()) {
            return ResponseEntity.ok(plansStatisticRepository.findByYear(
                    plansStatisticRequest.getUserId(),
                    plansStatisticRequest.getDate().getYear()));
        } else {
            return ResponseEntity.ok(plansStatisticRepository.findByMonth(
                    plansStatisticRequest.getUserId(),
                    plansStatisticRequest.getDate().getMonth().getValue()));
        }
    }

    @PostMapping("/collections")
    public ResponseEntity<?> getCollectionsStatistic(@RequestBody StatisticRequest statisticRequest) {
        if (userRepository.findById(statisticRequest.getUserId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        if (statisticRequest.isYear()) {
            return ResponseEntity.ok(collectionsStatisticRepository.findByYear(
                    statisticRequest.getUserId(),
                    statisticRequest.getDate().getYear()));
        } else {
            return ResponseEntity.ok(collectionsStatisticRepository.findByMonth(
                    statisticRequest.getUserId(),
                    statisticRequest.getDate().getMonth().getValue()));
        }
    }

    @PostMapping("/habits")
    public ResponseEntity<?> getHabitsStatistic(@RequestBody StatisticRequest statisticRequest) {
        if (userRepository.findById(statisticRequest.getUserId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        List<HabitsStatistic> habitsStatisticList;
        if (statisticRequest.isYear()) {
            habitsStatisticList = habitsStatisticRepository.findByYear(
                    statisticRequest.getUserId(),
                    statisticRequest.getDate().getYear());
        } else {
            habitsStatisticList = habitsStatisticRepository.findByMonth(
                    statisticRequest.getUserId(),
                    statisticRequest.getDate().getMonth().getValue());
        }

        for (HabitsStatistic habitsStatistic: habitsStatisticList) {
            habitsStatistic.setAmountOfEmpty(
                    (int) (getDaysInMonth(habitsStatistic.getAmountOfEmpty()) -
                            habitsStatistic.getAmountOfDone()));
        }

        return ResponseEntity.ok(habitsStatisticList);
    }

    public int getDaysInMonth(int monthNumber) {
        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), monthNumber);
        return yearMonth.lengthOfMonth();
    }
}
