package com.davidleston.landscapeWaterCapacity;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class ParallelPrefixWaterCapacityCalculator {
    public static BigInteger fromHeights(long... heights) {
        int reverseOffset = heights.length - 1;
        Iterator<long[]> arrays = Stream.<Supplier<long[]>>of(
                () -> {
                    long[] maxRight = Arrays.copyOf(heights, heights.length);
                    Arrays.parallelPrefix(maxRight, Long::max);
                    return maxRight;
                },
                () -> {
                    long[] maxLeft = new long[heights.length];
                    Arrays.parallelSetAll(maxLeft, index -> heights[reverseOffset - index]);
                    Arrays.parallelPrefix(maxLeft, Long::max);
                    return maxLeft;
                })
                .parallel()
                .map(Supplier::get)
                .iterator();
        long[] maxRight = arrays.next();
        long[] maxLeft = arrays.next();
        return IntStream.range(0, heights.length)
                .parallel()
                .mapToObj(index -> BigInteger.valueOf(Math.min(maxRight[index], maxLeft[reverseOffset - index]) - heights[index]))
                .reduce(BigInteger.ZERO, BigInteger::add);
    }
}
