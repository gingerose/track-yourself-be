package com.track.yourself.service;

import com.track.yourself.models.HabitItem;
import com.track.yourself.models.dto.CollectionDto;
import com.track.yourself.models.dto.FindCollectionsRequest;
import com.track.yourself.models.dto.FindHabitsRequest;
import com.track.yourself.models.dto.HabitItemDto;
import com.track.yourself.repository.CollectionRepository;
import com.track.yourself.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;

import static com.track.yourself.util.Util.*;

@Service
public class HabitService {
    @Autowired
    private HabitRepository habitRepository;

    public List<HabitItemDto> searchHabitsByParams(FindHabitsRequest findHabitsRequest) {
        if (findHabitsRequest.getYear() == null) {
            findHabitsRequest.setYear(Year.now().getValue());
        }

        if (findHabitsRequest.getMonth() == null) {
            findHabitsRequest.setMonth(LocalDate.now().getMonth().getValue());
        }

        if (findHabitsRequest.getPage() == null)
            findHabitsRequest.setPage(defaultPage);
        else if (findHabitsRequest.getAmount() == null)
            findHabitsRequest.setAmount(defaultHabitsAmount);

        List<Object[]> resultList = habitRepository.findLimited(
                findHabitsRequest.getUserId(),
                findHabitsRequest.getYear(),
                findHabitsRequest.getMonth(),
                PageRequest.of(findHabitsRequest.getPage(), findHabitsRequest.getAmount()));

        Map<Integer, HabitItemDto> idToHabitItemDto = new HashMap<>();
        for (Object[] result : resultList) {
            HabitItem habitItem = new HabitItem(
                    (Integer) result[2],
                    (Integer) result[0],
                    (Date) result[4],
                    (String) result[3]
            );
            if (idToHabitItemDto.get((Integer) result[0]) != null) {
                idToHabitItemDto.get((Integer) result[0]).addToList(habitItem);
            } else {
                HabitItemDto habitItemDto = new HabitItemDto();
                habitItemDto.setHabitId((Integer) result[0]);
                habitItemDto.setTitle((String) result[1]);
                habitItemDto.addToList(habitItem);
                idToHabitItemDto.put(habitItemDto.getHabitId(), habitItemDto);
            }
        }

        List<HabitItemDto> habits = new ArrayList<>(idToHabitItemDto.values());
        return habits;
    }
}
