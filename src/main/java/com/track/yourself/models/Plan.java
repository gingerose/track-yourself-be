package com.track.yourself.models;

import lombok.Data;

import javax.persistence.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;
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
  private String name;
  private String description;
  private String status;
  @Column(name = "creation_date")
  private Date creationDate;
  @Column(name = "day_of_week")
  private Integer dayOfWeek;
  private String priority;
  @Column(name = "modify_date")
  private LocalDateTime modifyDate;
  private Integer duration;
  private Date deadline;
}
