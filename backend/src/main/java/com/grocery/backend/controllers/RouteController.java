package com.grocery.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    @GetMapping("/routes")
    public ResponseEntity<String> getRoute(@RequestParam("orderId") int orderId){
        //TODO: implementation
        return ResponseEntity.ok().body("Order: " + orderId);
    }

}
