package com.track.yourself.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "notes")
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Integer noteId;
    @Column(name = "user_id")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Integer userId;
    private String title;
    private Date date;
    private String text;
    @Column(name = "category_id")
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Integer categoryId;
}

