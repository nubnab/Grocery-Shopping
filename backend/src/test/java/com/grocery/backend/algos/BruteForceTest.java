package com.grocery.backend.algos;

import org.junit.jupiter.api.Test;

class BruteForceTest {
    BruteForce bruteForce;

    @Test
    void printTest() {
        this.bruteForce = new BruteForce();
        bruteForce.solve(new int[][] {
                {0, 0}, {1, 2}, {3, 0}, {0, 4}, {2, 3}, {4, 4},
                {3, 1}, {1, 1}, {2, 5}, {4, 2}, {5, 2}}
                );
    }
}