package com.dhruv.asynchronusComputation_and_parallerProcessing;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamPerformance {

    public static void main(String[] args) {

        // Generating the list of 1000000 integers and collecting to the list.
        List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000)
                .boxed()
                .collect(Collectors.toList());

        long start = System.currentTimeMillis();
        long sum1 = numbers.stream() // Sequential Stream
                .mapToLong(Integer::longValue)
                .sum();
        long end = System.currentTimeMillis();
        System.out.println("Sequential Sum: " + sum1 + " , Time: " + (end - start) + "ms");


        start = System.currentTimeMillis();
        long sum2 = numbers.parallelStream() // Parallel Stream
                .mapToLong(Integer::longValue)
                .sum();
        end = System.currentTimeMillis();
        System.out.println("Parallel Sum: " + sum2 + " , Time: " + (end - start) + "ms");
    }
}