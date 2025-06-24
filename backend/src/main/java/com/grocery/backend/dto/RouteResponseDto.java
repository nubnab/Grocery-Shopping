package com.grocery.backend.dto;

import com.grocery.backend.enums.OrderStatus;

import java.util.ArrayList;

public record RouteResponseDto(Long orderId,
                               OrderStatus status,
                               ArrayList<int[]> visitedLocations) {

}
