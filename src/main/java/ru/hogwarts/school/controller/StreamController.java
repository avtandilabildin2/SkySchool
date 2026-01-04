package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("/stream")
public class StreamController {
    @GetMapping("/fast-sum")
    public int getFastSum() {
        return IntStream.rangeClosed(1, 1_000_000).sum();
    }
}
