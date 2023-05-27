package com.track.yourself.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "habit_items")
@AllArgsConstructor
@NoArgsConstructor
public class HabitItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;
    @Column(name = "habit_id")
    @JoinColumn(name = "habit_id", referencedColumnName = "habit_id")
    private Integer habitId;
    private Date date;
    private String status;
}
