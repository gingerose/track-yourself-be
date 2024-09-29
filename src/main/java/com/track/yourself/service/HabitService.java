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

    findHabitsRequest.setTitle(getParam(findHabitsRequest.getTitle()));

    List<Object[]> resultList = habitRepository.findLimited(
      findHabitsRequest.getUserId(),
      findHabitsRequest.getYear(),
      findHabitsRequest.getMonth(),
      findHabitsRequest.getTitle());

    Map<Integer, HabitItemDto> idToHabitItemDto = new HashMap<>();
    for (Object[] result : resultList) {
      HabitItem habitItem = new HabitItem(
        (Integer) result[3],
        (Integer) result[2],
        (Date) result[5],
        (String) result[4]
      );
      if (idToHabitItemDto.get((Integer) result[2]) != null && result[2] != null) {
        idToHabitItemDto.get((Integer) result[2]).addToList(habitItem);
      } else {
        HabitItemDto habitItemDto = new HabitItemDto();
        habitItemDto.setHabitId((Integer) result[1]);
        habitItemDto.setTitle((String) result[0]);
        habitItemDto.addToList(habitItem);
        idToHabitItemDto.put(habitItemDto.getHabitId(), habitItemDto);
      }
    }

    List<HabitItemDto> habits = new ArrayList<>(idToHabitItemDto.values());
    return habits;
  }
}
