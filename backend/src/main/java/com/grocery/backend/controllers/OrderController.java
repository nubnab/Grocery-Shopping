package com.grocery.backend.controllers;

import com.grocery.backend.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @GetMapping("/orders/{id}")
    public ResponseEntity<String> getOrder(@PathVariable Long id) {
        //TODO: implementation
        return ResponseEntity.ok().body(id.toString());
    }

    @PostMapping("/orders")
    public ResponseEntity<String> createOrder(@RequestBody List<OrderDto> orderList) {
        //TODO: implementation
        return ResponseEntity.ok().body(orderList.toString());
    }


}
