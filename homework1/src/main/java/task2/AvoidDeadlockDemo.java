package task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AvoidDeadlockDemo {
    private static volatile boolean readyToStop = false;
    private static final ReentrantLock collectionForProducingLock = new ReentrantLock();
    private static final ReentrantLock collectionForCalculationLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        runAvoidDeadlockDemo();
        Thread.sleep(10_000);
        readyToStop = true;
    }

    public static void runAvoidDeadlockDemo() throws InterruptedException {
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
                boolean iscollectionForProducingLockAcquired = false;
                try {
                    iscollectionForProducingLockAcquired = collectionForProducingLock.tryLock(1, TimeUnit.SECONDS);
                    printWaitingForResourceState(threadName, "collectionForProducing");
                    int element = ThreadLocalRandom.current().nextInt();
                    collectionForProducing.add(element);
                    printElementWasAddedToTheCollection("collectionForProducing", element);
                } catch (InterruptedException e) {
                    printCannotLock(threadName, "collectionForProducingLock");
                } finally {
                    if (iscollectionForProducingLockAcquired) {
                        collectionForProducingLock.unlock();
                        printLockIsReleased(threadName, "collectionForCalculationLock");
                    }
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
                boolean isCollectionForProducingLockAcquired = false;
                boolean isCollectionForCalculationLockAcquired = false;
                try {
                    isCollectionForProducingLockAcquired = collectionForProducingLock.tryLock(1, TimeUnit.SECONDS);
                    printResourceMonitorIsLock(threadName, "collectionForProducing");
                    int sum = collectionForProducing.stream()
                            .mapToInt(i -> i)
                            .sum();
                    printWaitingForResourceState(threadName, "collectionForCalculation");
                    isCollectionForCalculationLockAcquired = collectionForCalculationLock.tryLock(1, TimeUnit.SECONDS);
                    printResourceMonitorIsLock(threadName, "collectionForCalculation");
                    collectionForCalculation.add(sum);
                    printElementWasAddedToTheCollection("collectionForCalculation", sum);
                } catch (InterruptedException e) {
                    printCannotLock(threadName, "collectionForProducingLock or collectionForCalculationLock");
                } finally {
                    if (isCollectionForProducingLockAcquired) {
                        collectionForProducingLock.unlock();
                        printLockIsReleased(threadName, "collectionForProducingLock");
                    }
                    if (isCollectionForCalculationLockAcquired) {
                        collectionForCalculationLock.unlock();
                        printLockIsReleased(threadName, "isCollectionForCalculationLockAcquired");
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
                boolean isCollectionForProducingLockAcquired = false;
                boolean isCollectionForCalculationLockAcquired = false;
                try {
                    isCollectionForCalculationLockAcquired = collectionForCalculationLock.tryLock(1, TimeUnit.SECONDS);
                    printResourceMonitorIsLock(threadName, "collectionForCalculation");
                    int sum = collectionForProducing.stream()
                            .mapToInt(i -> i * i)
                            .sum();
                    double sqrt = Math.sqrt(sum);
                    printWaitingForResourceState(threadName, "collectionForProducing");
                    isCollectionForProducingLockAcquired = collectionForProducingLock.tryLock(1, TimeUnit.SECONDS);
                    printResourceMonitorIsLock(threadName, "collectionForProducing");
                    int element = (int) sqrt;
                    collectionForCalculation.add(element);
                    printElementWasAddedToTheCollection("collectionForProducing", element);
                } catch (InterruptedException e) {
                    printCannotLock(threadName, "collectionForProducingLock or collectionForCalculationLock");
                } finally {
                    if (isCollectionForProducingLockAcquired) {
                        collectionForProducingLock.unlock();
                        printLockIsReleased(threadName, "collectionForProducingLock");
                    }
                    if (isCollectionForCalculationLockAcquired) {
                        collectionForCalculationLock.unlock();
                        printLockIsReleased(threadName, "isCollectionForCalculationLockAcquired");
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

    private static void printCannotLock(String threadName, String lockName) {
        System.out.println("Thread " + threadName + " release lock " + lockName);
    }

    private static void printElementWasAddedToTheCollection(String collectionName, Integer element) {
        System.out.println("Element " + element + " was added to " + collectionName);
    }

    private static void printLockIsReleased(String threadName, String lockName) {
        System.out.println(threadName + " release lock " + lockName);
    }
}
