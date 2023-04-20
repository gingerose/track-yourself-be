package com.track.yourself.models.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String login;
    private String password;
    private String picture;
    private String username;
}
