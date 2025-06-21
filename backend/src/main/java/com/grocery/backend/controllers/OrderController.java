package com.grocery.backend.controllers;

import com.grocery.backend.dto.OrderDto;
import com.grocery.backend.services.OrderCreationService;
import com.grocery.backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderCreationService orderCreationService;
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<String> createOrder(@RequestBody List<OrderDto> items) {
        orderCreationService.create(items);
        return ResponseEntity.ok().body(items.toString());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<String> getOrder(@PathVariable Long id) {
        //TODO: implementation
        return ResponseEntity.ok().body(id.toString());
    }

}
