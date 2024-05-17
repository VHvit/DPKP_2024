package com.example.controllers;

import com.example.model.dto.BranchDto;
import com.example.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class AuthController {

    private final CityService cityService;

    @GetMapping("/auth")
    public void authCon() {
        cityService.select1();
    }
}
