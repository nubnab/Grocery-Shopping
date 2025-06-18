package com.grocery.backend.algos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BruteForceTest {
    BruteForce bruteForce;

    @Test
    void minCost_shouldBe24() {
        this.bruteForce = new BruteForce();
        bruteForce.solve(new int[][] {
                {0, 0}, {1, 2}, {3, 0}, {0, 4}, {2, 3}, {4, 4},
                {3, 1}, {1, 1}, {2, 5}, {4, 2}, {5, 2}}
                );

        assertEquals(24, bruteForce.getMinCost());
    }

    @Test
    void minCost_shouldBe0() {
        this.bruteForce = new BruteForce();
        bruteForce.solve(new int[][] {
                {0, 0}}
        );
        assertEquals(0, bruteForce.getMinCost());
    }
}