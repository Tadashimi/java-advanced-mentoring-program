package com.epam.server;

import com.epam.service.SimpleServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;
import java.util.logging.Logger;


public class SimpleServer {
    private Server server;
    private final Logger logger = Logger.getLogger(SimpleServer.class.getSimpleName());

    public void startServer() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new SimpleServiceImpl())
                .build()
                .start();
        logger.info("Server has started on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                SimpleServer.this.stopServer();
            } catch (InterruptedException e) {
                logger.severe("Thread was interrupted while stopping server");
            }
        }));
    }

    public void stopServer() throws InterruptedException {
        logger.info("Trying to stop server");
        if (nonNull(server)) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (nonNull(server)) {
            server.awaitTermination();
        }
    }

    //Uncomment to start only SimpleServer
//    public static void main(String[] args) throws InterruptedException, IOException {
//        SimpleServer server = new SimpleServer();
//        server.startServer();
//        server.blockUntilShutdown();
//    }

}
