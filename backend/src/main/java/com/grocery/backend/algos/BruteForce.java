package com.grocery.backend.algos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BruteForce {

    private int[][] distanceMatrix;
    private int numWarehouses;
    private int minCost = Integer.MAX_VALUE;
    private List<Integer> bestRoute = new ArrayList<>();

    public BruteForce() {}

    public void solve(int[][] coordinates) {
        init();
        createDistanceMatrix(coordinates);
        
        this.numWarehouses = distanceMatrix.length;
        List<Integer> warehouses = new ArrayList<>();
        for (int i = 1; i < numWarehouses; i++) {
            warehouses.add(i);
        }

        generateCombinations(warehouses, 0);

        System.out.println("Minimum path cost: " + minCost);
        System.out.println("Best route: " + bestRoute);
        printRoute(coordinates, bestRoute);
    }

    private void init() {
        this.minCost = Integer.MAX_VALUE;
        this.bestRoute = new ArrayList<>();
    }

    private void printRoute(int[][] coordinates, List<Integer> bestRoute) {
        for (int bestRouteIndex : bestRoute) {
            System.out.printf("(%d, %d) ", coordinates[bestRouteIndex][0], coordinates[bestRouteIndex][1]);
        }
    }

    private int calcDistance(int[] x, int[] y) {
        return Math.abs(x[0] - y[0]) + Math.abs(x[1] - y[1]);
    }

    private void createDistanceMatrix(int[][] coordinates) {
        this.numWarehouses = coordinates.length;
        this.distanceMatrix = new int[numWarehouses][numWarehouses];
        for (int i = 0; i < numWarehouses; i++) {
            for (int j = 0; j < numWarehouses; j++) {
                distanceMatrix[i][j] = calcDistance(coordinates[i], coordinates[j]);
            }
        }
    }

    private void generateCombinations(List<Integer> warehouses, int l) {
        if (l == warehouses.size()) {
            evaluatePath(warehouses);
            return;
        }

        for(int i = l; i < warehouses.size(); i++) {
            Collections.swap(warehouses, l, i);
            generateCombinations(warehouses, l + 1);
            Collections.swap(warehouses, l, i);
        }
    }

    private void evaluatePath(List<Integer> warehouses) {
        int cost = 0;
        int currentWarehouse = 0;

        for (int nextWarehouse : warehouses) {
            cost += distanceMatrix[currentWarehouse][nextWarehouse];
            if (cost >= minCost) return;
            currentWarehouse = nextWarehouse;
        }

        cost += distanceMatrix[currentWarehouse][0];

        if (cost < minCost) {
            minCost = cost;
            bestRoute = new ArrayList<>();
            bestRoute.add(0);
            bestRoute.addAll(warehouses);
            bestRoute.add(0);
        }
    }

}
