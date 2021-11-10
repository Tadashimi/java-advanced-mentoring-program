package com.epam.service;

import com.epam.stups.services.SimpleRequest;
import com.epam.stups.services.SimpleResponse;
import com.epam.stups.services.SimpleServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SimpleServiceImpl extends SimpleServiceGrpc.SimpleServiceImplBase {

    @Override
    public void getSimpleMessage(SimpleRequest request, StreamObserver<SimpleResponse> responseObserver) {
        String requestText = request.getText();
        SimpleResponse response = SimpleResponse.newBuilder()
                .setText(generateResponseText(requestText))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private String generateResponseText(String requestText) {
        switch (requestText) {
            case "Ping":
                return  "Pong";
            case "Hello":
                return "Hi";
            default:
                return "Don't understand";
        }
    }

}
