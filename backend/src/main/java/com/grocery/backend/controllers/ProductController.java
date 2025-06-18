package com.grocery.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/products")
    public ResponseEntity<String> getProduct(){
        return ResponseEntity.ok().body("Product controller works");
    }

    //TODO: POST, PUT, DELETE




}
