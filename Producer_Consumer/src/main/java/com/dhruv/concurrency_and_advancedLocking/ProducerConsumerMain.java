package com.dhruv.concurrency_and_advancedLocking;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ProducerConsumerMain {

    public static void main(String[] args) throws InterruptedException {


        MessageQueue queue = new MessageQueue();

        ExecutorService producerPool = Executors.newFixedThreadPool(3);
        ExecutorService consumerPool = Executors.newFixedThreadPool(3);

        for(int i=1;i<=3;i++){
            producerPool.submit(new MessageSender(queue, "Sender-" +i));
            consumerPool.submit(new MessageReceiver(queue, "Receiver-" + i));
        }

        producerPool.shutdown();
        consumerPool.shutdown();

        producerPool.awaitTermination(1, TimeUnit.MINUTES);
        consumerPool.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("All tasks completed");

    }
}