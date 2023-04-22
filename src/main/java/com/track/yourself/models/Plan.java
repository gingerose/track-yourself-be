package com.track.yourself.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Integer planId;
    @Column(name = "user_id")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Integer user_id;
    private String description;
    private String status;
    private Date date;
}
