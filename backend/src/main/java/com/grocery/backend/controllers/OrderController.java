package com.grocery.backend.controllers;

import com.grocery.backend.dto.OrderDto;
import com.grocery.backend.dto.OrderStatusDto;
import com.grocery.backend.enums.OrderStatus;
import com.grocery.backend.services.OrderCreationService;
import com.grocery.backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderCreationService orderCreationService;
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Object> createOrder(@RequestBody List<OrderDto> items) {
        orderCreationService.create(items);
        Map<String, Object> body = new HashMap<>();
        body.put("status", OrderStatus.SUCCESS);
        body.put("message", "Your order is ready! Please collect it.");
        return ResponseEntity.ok(body);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderStatusDto> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok().body(orderService.findById(id));
    }

}
