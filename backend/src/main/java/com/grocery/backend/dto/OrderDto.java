package com.grocery.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderDto(
        @NotNull
        String productName,

        @Min(0)
        @NotNull
        int quantity) {

}
