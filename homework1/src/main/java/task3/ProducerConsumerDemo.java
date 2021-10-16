package task3;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerDemo {
    private static final int THREAD_COUNT = 5;
    private static final int CAPACITY = 100;
    private static final int NUMBER_OF_OPERATIONS = 1_000;

    public static void main(String[] args) throws InterruptedException {
        Queue<String> myQueue = new LinkedList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            String producerThreadName = "producerThread" + i;
            Thread producerThread = new Thread(createProducerRunnable(myQueue, producerThreadName), producerThreadName);
            String consumerThreadName = "consumerThread" + i;
            Thread consumerThread = new Thread(createConsumerRunnable(myQueue, consumerThreadName), consumerThreadName);
            producerThread.start();
            consumerThread.start();
            producerThread.join();
            consumerThread.join();
        }
    }

    private static Runnable createConsumerRunnable(Queue<String> myQueue, String threadName) {
        return () -> {
            MyConsumer consumer = new MyConsumer(myQueue);
            for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
                try {
                    String value = consumer.consume();
                    System.out.println(threadName + " read value = " + value);
                } catch (InterruptedException e) {
                    System.out.println("Ooops. Exception occurs");
                }
            }
        };
    }

    private static Runnable createProducerRunnable(Queue<String> myQueue, String threadName) {
        return () -> {
            MyProducer producer = new MyProducer(myQueue, CAPACITY);
            for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
                try {
                    String value = "My string " + i;
                    producer.produce(value);
                    System.out.println(threadName + " added value = " + value);
                } catch (InterruptedException e) {
                    System.out.println("Ooops. Exception occurs");
                }
            }
        };
    }
}
