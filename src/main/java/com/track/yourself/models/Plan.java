package com.track.yourself.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "plans")
public class Plan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "plan_id")
  private Integer planId;
  @Column(name = "user_id")
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private Integer userId;
  private String description;
  private String status;
  private Date date;
  @Column(name = "day_of_week")
  private Integer dayOfWeek;
}
