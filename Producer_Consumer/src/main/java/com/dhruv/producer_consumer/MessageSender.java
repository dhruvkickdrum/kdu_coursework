package com.dhruv.producer_consumer;

import java.time.LocalDateTime;

public class MessageSender implements Runnable {

    private final MessageQueue queue;
    private final String senderName;

    public MessageSender(MessageQueue queue, String senderName) {
        this.queue = queue;
        this.senderName = senderName;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            String message = senderName + " - Message " + i + " @ " + LocalDateTime.now();
            queue.put(message);
            System.out.println("Produced: " + message);

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}