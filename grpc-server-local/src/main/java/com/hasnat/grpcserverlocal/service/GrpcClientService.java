package com.hasnat.grpcserverlocal.service;

import com.hasnat.proto.greetings.GreetingRequest;
import com.hasnat.proto.greetings.GreetingResponse;
import com.hasnat.proto.greetings.GreetingServiceGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {
    @GrpcClient("grpc-client-local")
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
