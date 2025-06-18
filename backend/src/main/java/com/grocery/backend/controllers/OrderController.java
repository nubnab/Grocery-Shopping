package com.grocery.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/orders") //TODO + /{id}, specific order only
    public ResponseEntity<String> getOrder(){
        return ResponseEntity.ok().body("Order controller works");
    }

    //TODO: POST



}
