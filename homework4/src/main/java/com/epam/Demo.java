package com.epam;

import com.epam.client.SimpleClient;
import com.epam.server.SimpleServer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Demo {
    private static final Logger LOGGER = Logger.getLogger(Demo.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException {
        Thread serverThread = new Thread(() -> {
            LOGGER.info("Creating a server");
            SimpleServer server = new SimpleServer();
            LOGGER.info("Starting a server");
            try {
                server.startServer();
                server.blockUntilShutdown();
                LOGGER.info("Server has started");
            } catch (Exception e) {
                LOGGER.severe("Something goes wrong");
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();

        Thread clientThread = new Thread(() -> {
            LOGGER.info("Creating a channel");
            ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50051")
                    .usePlaintext().build();
            LOGGER.info("Creating a client");
            SimpleClient client = new SimpleClient(channel);
            LOGGER.info("Send message Ping... Response = " + client.getMessage("Ping"));
            LOGGER.info("Send message Hello... Response = " + client.getMessage("Hello"));
            LOGGER.info("Send message Lalala... Response = " + client.getMessage("Lalala"));
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                LOGGER.severe("Thread was interrupted while channel shutting down");
            }
        });
        clientThread.setDaemon(true);
        clientThread.start();

        serverThread.join();
        clientThread.join();
    }

}
