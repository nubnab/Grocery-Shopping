package com.grocery.backend.dto;

import com.grocery.backend.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusDto (
        @NotNull
        Long id,

        @NotNull
        OrderStatus status) {

}
