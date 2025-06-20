package com.grocery.backend.dto;

import com.grocery.backend.embeds.Location;

public record ProductDto(String name,
                         double price,
                         int quantity,
                         Location location) {

}