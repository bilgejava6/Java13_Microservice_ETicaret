package com.muhammet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackAuthController {
    @GetMapping("/auth")
    public String fallbackauth() {
        return "Auth service is not available. Please try again later.";
    }

    @GetMapping("/user")
    public String fallbackuser() {
        return "User service is not available. Please try again later.";
    }

}
