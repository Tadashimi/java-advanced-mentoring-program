package task1.mapscomparing.singlethreadsdemo;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MapsComparingDemo {
    private static final int NUMBER_OF_OPERATIONS = 1_000;
    private static final String WRITER = "WRITER";
    private static final String ADDER = "ADDER";
    private static long hashMapTime;
    private static long synchronizedMapTime;
    private static long concurrentHashMapTime;

    public static void main(String[] args) throws InterruptedException {
        runHashMapDemo(NUMBER_OF_OPERATIONS);
        runConcurrentHashMapDemo(NUMBER_OF_OPERATIONS);
        runSynchronizedMapDemo(NUMBER_OF_OPERATIONS);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.printf("%d ms took HashMap to complete on %d values\n", hashMapTime, NUMBER_OF_OPERATIONS);
            System.out.printf("%d ms took SynchronizedMap to complete on %d values\n", synchronizedMapTime, NUMBER_OF_OPERATIONS);
            System.out.printf("%d ms took ConcurrentHashMap to complete on %d values\n", concurrentHashMapTime, NUMBER_OF_OPERATIONS);
        }));
    }

    public static void runHashMapDemo(int numberOfOperations) throws InterruptedException {
        Map<Integer, Integer> hashMap = new HashMap<>();
        Runnable putElementUsingSynchronized = () -> {
            for (int i = 0; i < numberOfOperations; i++) {
                synchronized (hashMap) {
                    hashMap.put(i, ThreadLocalRandom.current().nextInt());
                }
            }
        };
        Runnable calculateSumOfAllElementsUsingSynchronized = () -> {
            for (int i = 0; i < numberOfOperations; i++) {
                synchronized (hashMap) {
                    //Use single long type as we will calculate only in one thread simultaneously
                    long sum = 0;
                    for (Integer value : hashMap.values()) {
                        sum += value;
                    }
                }
            }
        };

        Thread writerThread = new Thread(putElementUsingSynchronized, WRITER);
        Thread adderThread = new Thread(calculateSumOfAllElementsUsingSynchronized, ADDER);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        writerThread.start();
        adderThread.start();
        writerThread.join();
        adderThread.join();
        stopWatch.stop();

        hashMapTime = stopWatch.getTime(TimeUnit.MILLISECONDS);
    }

    public static void runSynchronizedMapDemo(int numberOfOperations) throws InterruptedException {
        Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        Runnable putElementUsingSynchronized = () -> {
            for (int i = 0; i < numberOfOperations; i++) {
                synchronizedMap.put(i, ThreadLocalRandom.current().nextInt());
            }
        };
        Runnable calculateSumOfAllElementsUsingSynchronized = () -> {
            for (int i = 0; i < numberOfOperations; i++) {
                synchronized (synchronizedMap) {
                    //Use single long type as we will calculate only in one thread simultaneously
                    long sum = 0;
                    for (Integer value : synchronizedMap.values()) {
                        sum += value;
                    }
                }
            }
        };

        Thread writerThread = new Thread(putElementUsingSynchronized, WRITER);
        Thread adderThread = new Thread(calculateSumOfAllElementsUsingSynchronized, ADDER);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        writerThread.start();
        adderThread.start();
        writerThread.join();
        adderThread.join();
        stopWatch.stop();

        synchronizedMapTime = stopWatch.getTime(TimeUnit.MILLISECONDS);
    }

    public static void runConcurrentHashMapDemo(int numberOfOperations) throws InterruptedException {
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Runnable putElementUsingSynchronized = () -> {
            for (int i = 0; i < numberOfOperations; i++) {
                concurrentHashMap.put(i, ThreadLocalRandom.current().nextInt());
            }
        };
        Runnable calculateSumOfAllElementsUsingSynchronized = () -> {
            for (int i = 0; i < numberOfOperations; i++) {
                synchronized (concurrentHashMap) {
                    //Use single long type as we will calculate only in one thread simultaneously
                    long sum = 0;
                    for (Integer value : concurrentHashMap.values()) {
                        sum += value;
                    }
                }
            }
        };

        Thread writerThread = new Thread(putElementUsingSynchronized, WRITER);
        Thread adderThread = new Thread(calculateSumOfAllElementsUsingSynchronized, ADDER);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        writerThread.start();
        adderThread.start();
        writerThread.join();
        adderThread.join();
        stopWatch.stop();

        concurrentHashMapTime = stopWatch.getTime(TimeUnit.MILLISECONDS);
    }
}
