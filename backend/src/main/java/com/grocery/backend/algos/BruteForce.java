package com.grocery.backend.algos;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class BruteForce {

    private int[][] distanceMatrix;
    private int numWarehouses;
    private int minCost = Integer.MAX_VALUE;
    private int[] bestRoute;

    public BruteForce() {}

    public int[][] solve(int[][] coordinates) {
        init();
        createDistanceMatrix(coordinates);

        this.numWarehouses = distanceMatrix.length;

        int[] warehouses = new int[numWarehouses];
        for (int i = 1; i < numWarehouses; i++) {
            warehouses[i - 1] = i;
        }

        generateCombinations(warehouses, 0);

        int[][] solution = new int[bestRoute.length][2];
        for (int i = 0; i < bestRoute.length; i++) {
            solution[i][0] = coordinates[bestRoute[i]][0];
            solution[i][1] = coordinates[bestRoute[i]][1];
        }

        printRoute(solution, bestRoute);

        return solution;
    }

    private void init() {
        this.minCost = Integer.MAX_VALUE;
        this.bestRoute = null;
    }

    private void printRoute(int[][] coordinates, int[] bestRoute) {
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
            for (int j = i + 1; j < numWarehouses; j++) {
                int dist = calcDistance(coordinates[i], coordinates[j]);
                distanceMatrix[i][j] = dist;
                distanceMatrix[j][i] = dist;
            }
        }
    }

    private void generateCombinations(int[] warehouses, int l) {
        if (l == warehouses.length) {
            evaluatePath(warehouses);
            return;
        }

        for(int i = l; i < warehouses.length; i++) {
            swap(warehouses, l, i);
            generateCombinations(warehouses, l + 1);
            swap(warehouses, l, i);
        }
    }

    private void evaluatePath(int[] warehouses) {
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
            bestRoute = new int[warehouses.length + 1];
            bestRoute[0] = 0;
            System.arraycopy(warehouses, 0, bestRoute, 1, warehouses.length);
            bestRoute[bestRoute.length - 1] = 0;
        }
    }

    private void swap(int[] warehouses, int i, int j) {
        int tmp = warehouses[i];
        warehouses[i] = warehouses[j];
        warehouses[j] = tmp;
    }

}
