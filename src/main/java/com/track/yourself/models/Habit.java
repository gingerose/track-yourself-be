package com.track.yourself.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "habits")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_id")
    private Integer habitId;
    @Column(name = "user_id")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Integer userId;
    private String title;
    private Date date;
}
