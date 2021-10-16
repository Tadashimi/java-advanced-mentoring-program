package task4;

import java.util.concurrent.ThreadLocalRandom;

public class BlockingObjectPoolDemo {
    private static final int THREAD_COUNT = 5;
    private static final int CAPACITY = 100;
    private static final int NUMBER_OF_OPERATIONS = 1_000;

    public static void main(String[] args) throws InterruptedException {
        BlockingObjectPool myPool = new BlockingObjectPool(CAPACITY);
        for (int i = 0; i < THREAD_COUNT; i++) {
            String setterThreadName = "Thread that put values to the queue #" + i;
            Thread setterThread = new Thread(putToTheQueue(myPool, setterThreadName), setterThreadName);
            String getterThreadName = "Thread that get from the queue values #" + i;
            Thread getterThread = new Thread(getFromTheQueue(myPool, getterThreadName), getterThreadName);
            setterThread.start();
            getterThread.start();
            setterThread.join();
            getterThread.join();
        }
    }

    private static Runnable putToTheQueue(BlockingObjectPool myPool, String threadName) {
        return () -> {
            for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
                Integer value = ThreadLocalRandom.current().nextInt();
                try {
                    myPool.take(value);
                    System.out.println(threadName + " put into the queue value = " + value);
                } catch (IllegalStateException e) {
                    System.out.println("Ooops, queue is full");
                }
            }
        };
    }

    private static Runnable getFromTheQueue(BlockingObjectPool myPool, String threadName) {
        return () -> {
            for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
                try {
                    Object value = myPool.get();
                    System.out.println(threadName + " get from the queue value = " + value);
                } catch (IllegalStateException e) {
                    System.out.println("Ooops, queue is full");
                }
            }
        };
    }
}
