package com.track.yourself.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private Integer userId;
    private String login;
    private String picture;
    private String username;
}
