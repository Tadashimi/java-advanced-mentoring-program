package com.epam.client;

import com.epam.stups.services.SimpleRequest;
import com.epam.stups.services.SimpleResponse;
import com.epam.stups.services.SimpleServiceGrpc;
import io.grpc.Channel;

public class SimpleClient {
    private SimpleServiceGrpc.SimpleServiceBlockingStub simpleServiceBlockingStub;

    public SimpleClient(Channel channel) {
        simpleServiceBlockingStub = SimpleServiceGrpc.newBlockingStub(channel);
    }

    public String getMessage(String requestText) {
        SimpleRequest request = SimpleRequest.newBuilder().setText(requestText).build();
        SimpleResponse simpleResponse = simpleServiceBlockingStub.getSimpleMessage(request);
        return simpleResponse.getText();
    }
}
