package com.dhruv.producer_consumer;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
    private final Queue<String> queue = new LinkedList<>();

    public synchronized void put(String message) {
        queue.add(message);
        notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while(queue.isEmpty()) {
            wait();
        }
        return queue.poll();
    }
}


