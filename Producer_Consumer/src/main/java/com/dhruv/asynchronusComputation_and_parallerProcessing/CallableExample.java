package com.dhruv.asynchronusComputation_and_parallerProcessing;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Integer> task = () -> {
            int sum = 0;
            for(int i=1;i<=100;i++){
                sum += i;
            }
            return sum;
        };

        Future<Integer> future = executor.submit(task); // Submit the task to the future

        System.out.println("Waiting for result..."); // block until result is available
        System.out.println("Sum = " + future.get());

        executor.shutdown();
    }
}