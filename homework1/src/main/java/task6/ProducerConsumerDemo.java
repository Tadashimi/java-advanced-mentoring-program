package task6;

import org.apache.commons.lang3.time.StopWatch;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerDemo {
    private static final int NUMBER_OF_OPERATIONS = 1_000_000;
    private static final int CAPACITY = 10_000;
    private static long classicModelTime;
    private static long concurrencyModelTime;

    public static void main(String[] args) throws InterruptedException {
        runClassicModelDemo();
        runConcurrencyModelDemo();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.printf("%d ms took LinkedList to complete on %d values\n", classicModelTime, NUMBER_OF_OPERATIONS);
            System.out.printf("%d ms took ArrayBlockingQueue to complete on %d values\n", concurrencyModelTime, NUMBER_OF_OPERATIONS);
        }));
    }

    private static void runClassicModelDemo() throws InterruptedException {
        Queue<Integer> classicQueue = new LinkedList<>();

        Thread putElementUsingSynchronized = new Thread(() -> {
            for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
                synchronized (classicQueue) {
                    classicQueue.add(ThreadLocalRandom.current().nextInt());
                }
            }
        });
        Thread getElementUsingSynchronized = new Thread(() -> {
            for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
                synchronized (classicQueue) {
                    classicQueue.poll();
                }
            }
        });

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        putElementUsingSynchronized.start();
        getElementUsingSynchronized.start();
        putElementUsingSynchronized.join();
        getElementUsingSynchronized.join();
        stopWatch.stop();

        classicModelTime = stopWatch.getTime(TimeUnit.MILLISECONDS);
    }

    private static void runConcurrencyModelDemo() throws InterruptedException {
        Queue<Integer> classicQueue = new ArrayBlockingQueue<>(CAPACITY);

        Thread putElementWithoutSynchronized = new Thread(() -> {
            for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
                try {
                    classicQueue.add(ThreadLocalRandom.current().nextInt());
                } catch (IllegalStateException e) {
                }
            }
        });
        Thread getElementWithoutSynchronized = new Thread(() -> {
            for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
                try {
                    classicQueue.poll();
                } catch (IllegalStateException e) {
                }
            }
        });

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        putElementWithoutSynchronized.start();
        getElementWithoutSynchronized.start();
        putElementWithoutSynchronized.join();
        getElementWithoutSynchronized.join();
        stopWatch.stop();

        concurrencyModelTime = stopWatch.getTime(TimeUnit.MILLISECONDS);
    }
}
