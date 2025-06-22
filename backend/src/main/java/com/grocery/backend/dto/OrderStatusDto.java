package com.grocery.backend.dto;

import com.grocery.backend.enums.OrderStatus;

public record OrderStatusDto (Long id, OrderStatus status) {

}
