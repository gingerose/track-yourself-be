package com.track.yourself.models.dto;

import com.track.yourself.models.HabitItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class HabitItemDto {
    private Integer habitId;
    private String title;
    private List<HabitItem> habitItems = new ArrayList<>();

    public void addToList(HabitItem habitItem) {
        this.habitItems.add(habitItem);
    }
}
