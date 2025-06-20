package com.grocery.backend.dto;

import com.grocery.backend.embeds.Location;

public record ProductDto(Long id,
                         String name,
                         int quantity,
                         double price,
                         Location location) {

}