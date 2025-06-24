package com.grocery.backend.controllers;

import com.grocery.backend.dto.RouteResponseDto;
import com.grocery.backend.services.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/routes")
    public ResponseEntity<RouteResponseDto> getRoute(@RequestParam("orderId") Long orderId){
        return ResponseEntity.ok(routeService.findRouteByOrderId(orderId));
    }

}
