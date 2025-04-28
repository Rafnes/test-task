package me.dineka.comfortsoft.controller;

import me.dineka.comfortsoft.service.FindService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class FindControiler {
    private final FindService findService;

    public FindControiler(FindService findService) {
        this.findService = findService;
    }

    @PostMapping("/findNthMin")
    public ResponseEntity<Integer> findNthMinNumber(@RequestParam String path,
                                                    @RequestParam int n) {
        return ResponseEntity.ok(findService.findNthMinNum(path, n));
    }
}
