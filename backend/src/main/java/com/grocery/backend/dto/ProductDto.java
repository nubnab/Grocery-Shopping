package com.grocery.backend.dto;

import com.grocery.backend.embeds.Location;
import jakarta.validation.constraints.Min;

public record ProductDto(
        Long id,
        String name,

        @Min(0)
        int quantity,

        @Min(0)
        double price,

        Location location) {

}