package com.track.yourself.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "login")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    private String login;
    private String password;
    private String picture;
    private String username;

    public User(String login, String password, String picture, String username) {
        this.login = login;
        this.password = password;
        this.picture = picture;
        this.username = username;
    }
}
