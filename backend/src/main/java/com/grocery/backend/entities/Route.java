package com.grocery.backend.entities;

import com.grocery.backend.embeds.Location;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ElementCollection
    @CollectionTable(
            name = "route_locations",
            joinColumns = @JoinColumn(name = "route_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "x",
                    column = @Column(name = "location_x", nullable = false)),
            @AttributeOverride(name = "y",
                    column = @Column(name = "location_y", nullable = false))})
    private List<Location> visitedLocations;

}
