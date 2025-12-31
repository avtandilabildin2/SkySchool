package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @Value("${spring.profiles.active:default}")
    private String profile;

    @GetMapping("/info")
    public String info() {
        return "Application is running. Active profile: " + profile;
    }
}

