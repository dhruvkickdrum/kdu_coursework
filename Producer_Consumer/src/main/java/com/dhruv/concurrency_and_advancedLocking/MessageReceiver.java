package com.dhruv.concurrency_and_advancedLocking;

public class MessageReceiver implements Runnable {

    private final MessageQueue queue;
    private final String receiverName;

    public MessageReceiver(MessageQueue queue, String receiverName) {
        this.queue = queue;
        this.receiverName = receiverName;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                String message = queue.take();
                System.out.println(receiverName + " consumed: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}