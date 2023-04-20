package com.track.yourself.models.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {
    private String login;
    private String password;
}
