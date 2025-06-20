package com.grocery.backend.entities;

import com.grocery.backend.embeds.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x",
                    column = @Column(name = "location_x", nullable = false)),
            @AttributeOverride(name = "y",
                    column = @Column(name = "location_y", nullable = false)),
    })
    private Location location;

    @Column(unique = true, nullable = false)
    private String normalizedName;

}
