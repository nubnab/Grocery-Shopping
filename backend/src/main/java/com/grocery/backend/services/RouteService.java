package com.grocery.backend.services;

import com.grocery.backend.algos.BruteForce;
import com.grocery.backend.embeds.Location;
import com.grocery.backend.entities.Route;
import com.grocery.backend.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        System.out.println();

        List<Location> visitedLocations = new ArrayList<>();

        for (int i = 1; i < locations.size() + 1; i++) {
            visitedLocations.add(new Location(minCostRoute[i][0], minCostRoute[i][1]));
        }

        return Route.builder()
                .visitedLocations(visitedLocations)
                .build();
    }

}
