package com.jonatan.travel_assistant_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonatan.travel_assistant_api.service.external.AuthDemoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth-demo")
public class AuthDemoController {

    private final AuthDemoService authDemoService;

    @GetMapping("/bearer")
    public String testBearerAuth(){
        return authDemoService.testBearerAuth();
    }


    
}
