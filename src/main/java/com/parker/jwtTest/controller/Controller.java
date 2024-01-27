package com.parker.jwtTest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/demo")
public class Controller {
    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("From Secured End Point");
    }

}
