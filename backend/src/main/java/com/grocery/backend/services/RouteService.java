package com.grocery.backend.services;

import com.grocery.backend.algos.BruteForce;
import com.grocery.backend.dto.RouteResponseDto;
import com.grocery.backend.embeds.Location;
import com.grocery.backend.entities.Order;
import com.grocery.backend.entities.Route;
import com.grocery.backend.enums.OrderStatus;
import com.grocery.backend.exceptions.ResourceNotFoundException;
import com.grocery.backend.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    public Route createRoute(List<Location> locations) {
        BruteForce bruteForce = new BruteForce();

        int[][] coordinates = new int[locations.size() + 1][2];
        coordinates[0][0] = 0;
        coordinates[0][1] = 0;

        for(int i = 1; i < locations.size() + 1; i++) {
            coordinates[i][0] = locations.get(i - 1).getX();
            coordinates[i][1] = locations.get(i - 1).getY();
        }

        int[][] minCostRoute = bruteForce.solve(coordinates);

        List<Location> visitedLocations = new ArrayList<>();

        for (int i = 1; i < locations.size() + 1; i++) {
            visitedLocations.add(new Location(minCostRoute[i][0], minCostRoute[i][1]));
        }

        return Route.builder()
                .visitedLocations(visitedLocations)
                .build();
    }

    public RouteResponseDto findRouteByOrderId (Long orderId) {
        Route route = routeRepository.findRouteByOrderId(orderId).orElseThrow();
        Order order = route.getOrder();

        return new RouteResponseDto(
                order.getId(),
                order.getOrderStatus(),
                getVisitedLocations(route.getVisitedLocations()));
    }

    private ArrayList<int[]> getVisitedLocations(List<Location> locations) {
        ArrayList<int[]> visitedLocations = new ArrayList<>();
        for (Location location : locations) {
            visitedLocations.add(new int[] {location.getX(), location.getY()});
        }
        return visitedLocations;
    }

}
