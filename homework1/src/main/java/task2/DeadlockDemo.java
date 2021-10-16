package task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class DeadlockDemo {
    private static volatile boolean readyToStop = false;

    public static void main(String[] args) throws InterruptedException {
        runDeadlockDemo();
        Thread.sleep(10_000);
        readyToStop = true;
    }

    public static void runDeadlockDemo() throws InterruptedException {
        Collection<Integer> collectionForProducing = new ArrayList<>();
        Collection<Integer> collectionForCalculation = new ArrayList<>();

        Thread firstThread = new Thread(getFirstThread(collectionForProducing, "Thread1"), "Thread1");
        Thread secondThread = new Thread(getSecondThread(collectionForProducing, collectionForCalculation, "Thread2"));
        Thread thirdThread = new Thread(getThirdThread(collectionForProducing, collectionForCalculation, "Thread3"));

        firstThread.start();
        secondThread.start();
        thirdThread.start();
    }

    private static Runnable getFirstThread(Collection<Integer> collectionForProducing, String threadName) {
        return () -> {
            printRunningState(threadName);
            while (!readyToStop) {
                printWaitingForResourceState(threadName, "collectionForProducing");
                synchronized (collectionForProducing) {
                    printWaitingForResourceState(threadName, "collectionForProducing");
                    int element = ThreadLocalRandom.current().nextInt();
                    collectionForProducing.add(element);
                    printElementWasAddedToTheCollection("collectionForProducing", element);
                }
            }
            printThreadIsStopped(threadName);
        };
    }

    private static Runnable getSecondThread(Collection<Integer> collectionForProducing, Collection<Integer> collectionForCalculation, String threadName) {
        return () -> {
            printRunningState(threadName);
            while (!readyToStop) {
                printWaitingForResourceState(threadName, "collectionForProducing");
                synchronized (collectionForProducing) {
                    printResourceMonitorIsLock(threadName, "collectionForProducing");
                    int sum = collectionForProducing.stream()
                            .mapToInt(i -> i)
                            .sum();
                    printWaitingForResourceState(threadName, "collectionForCalculation");
                    synchronized (collectionForCalculation) {
                        printResourceMonitorIsLock(threadName, "collectionForCalculation");
                        collectionForCalculation.add(sum);
                        printElementWasAddedToTheCollection("collectionForCalculation", sum);
                    }
                }
            }
            printThreadIsStopped(threadName);
        };
    }

    private static Runnable getThirdThread(Collection<Integer> collectionForProducing, Collection<Integer> collectionForCalculation, String threadName) {
        return () -> {
            printRunningState(threadName);
            while (!readyToStop) {
                printWaitingForResourceState(threadName, "collectionForCalculation");
                synchronized (collectionForCalculation) {
                    printResourceMonitorIsLock(threadName, "collectionForCalculation");
                    int sum = collectionForProducing.stream()
                            .mapToInt(i -> i * i)
                            .sum();
                    double sqrt = Math.sqrt(sum);
                    printWaitingForResourceState(threadName, "collectionForProducing");
                    synchronized (collectionForProducing) {
                        printResourceMonitorIsLock(threadName, "collectionForProducing");
                        int element = (int) sqrt;
                        collectionForCalculation.add(element);
                        printElementWasAddedToTheCollection("collectionForProducing", element);
                    }
                }
            }
            printThreadIsStopped(threadName);
        };
    }

    private static void printRunningState(String name) {
        System.out.println("Thread " + name + " is running");
    }

    private static void printWaitingForResourceState(String name, String collection) {
        System.out.println("Thread " + name + " waiting for " + collection);
    }

    private static void printResourceMonitorIsLock(String name, String collection) {
        System.out.println(collection + " was locked by thread " + name);
    }

    private static void printThreadIsStopped(String name) {
        System.out.println("Thread " + name + " is stopped");
    }

    private static void printElementWasAddedToTheCollection(String collectionName, Integer element) {
        System.out.println("Element " + element + " was added to " + collectionName);
    }
}
