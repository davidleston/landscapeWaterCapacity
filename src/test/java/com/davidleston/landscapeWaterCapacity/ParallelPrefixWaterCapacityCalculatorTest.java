package com.davidleston.landscapeWaterCapacity;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static com.davidleston.landscapeWaterCapacity.ParallelPrefixWaterCapacityCalculator.fromHeights;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ParallelPrefixWaterCapacityCalculatorTest {

    @Test
    public void empty() {
        assertEquals(BigInteger.ZERO, fromHeights());
    }

    @Test
    public void oneValue() {
        assertEquals(BigInteger.ZERO, fromHeights(42));
    }

    @Test
    public void oneDuplicatedValue() {
        assertEquals(BigInteger.ZERO, fromHeights(42, 42));
    }

    @Test
    public void twoValues() {
        assertEquals(BigInteger.ZERO, fromHeights(1, 2));
    }

    @Test
    public void smallestValley() {
        assertEquals(BigInteger.ONE, fromHeights(2, 1, 2));
    }

    @Test
    public void smallestMountain() {
        assertEquals(BigInteger.ZERO, fromHeights(1, 2, 1));
    }

    @Test
    public void equalPeaks() {
        assertEquals(BigInteger.valueOf(2), fromHeights(1, 2, 1, 1, 2, 1));
    }

    @Disabled("Used to measure performance during development")
    @Test
    public void performance() {
        Random random = new Random(1);
        long[] heights = new long[100_000_000];
        for (int i = 0; i < heights.length; i++) {
            heights[i] = Math.abs(random.nextLong());
        }
        BigInteger volumeOfWater = fromHeights(heights);
        System.out.println(volumeOfWater);
    }

}
