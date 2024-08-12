package com.jwt.authentication.controller;

import jakarta.persistence.Entity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PageController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("user")
    public String getUserProfile(){
        return "user_profile";
    }
}
