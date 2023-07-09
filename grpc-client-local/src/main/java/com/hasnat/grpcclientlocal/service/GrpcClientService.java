package com.hasnat.grpcclientlocal.service;

import com.hasnat.grpc.generated.GreetingRequest;
import com.hasnat.grpc.generated.GreetingResponse;
import com.hasnat.grpc.generated.GreetingServiceGrpc;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {
    @GrpcClient("grpc-server-local")
    private GreetingServiceGrpc.GreetingServiceBlockingStub greetingServiceBlockingStub;

    public String sendMessage(String message) {
        GreetingRequest requestMessage = GreetingRequest.newBuilder().setMessage(message).build();
        try {
            final GreetingResponse response = this.greetingServiceBlockingStub.greeting(requestMessage);
            return response.getMessage();

        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }
}
