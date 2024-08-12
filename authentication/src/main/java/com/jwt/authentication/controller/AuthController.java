package com.jwt.authentication.controller;

import com.jwt.authentication.dto.AuthResponse;
import com.jwt.authentication.dto.LoginRequest;
import com.jwt.authentication.dto.RegisterRequest;
import com.jwt.authentication.service.JwtService;
import com.jwt.authentication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class AuthController {

    private final UserService userService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtService jwtService;

    public AuthController(UserService userService, DaoAuthenticationProvider daoAuthenticationProvider, JwtService jwtService) {
        this.userService = userService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.jwtService = jwtService;
    }


    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            String result = userService.register(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<>("can not registered.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        Authentication authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(authentication);

        AuthResponse response = new AuthResponse(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
