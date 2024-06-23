package com.medhead.authservice.controller;

import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import com.medhead.authservice.exception.AuthenticationFailedException;
import com.medhead.authservice.dto.JwtResponse;
import com.medhead.authservice.dto.LoginRequest;
import com.medhead.authservice.dto.RegisterRequest;
import com.medhead.authservice.model.User;
import com.medhead.authservice.security.JwtUtil;
import com.medhead.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userService.findByUsername(registerRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

            return ResponseEntity.ok(new JwtResponse(jwtToken));
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Invalid username or password");
        }
    }

}
