package com.track.yourself.controllers;

import com.track.yourself.configs.jwt.JwtUtils;
import com.track.yourself.models.User;
import com.track.yourself.models.dto.JwtResponse;
import com.track.yourself.models.dto.LoginRequest;
import com.track.yourself.models.dto.MessageResponse;
import com.track.yourself.models.dto.SignupRequest;
import com.track.yourself.repository.UserRepository;
import com.track.yourself.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequest.getLogin(),
                loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getPicture(),
                userDetails.getName()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByLogin(signupRequest.getLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist!"));
        }

        User user = new User(signupRequest.getLogin(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getPicture(),
                signupRequest.getUsername());

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}
