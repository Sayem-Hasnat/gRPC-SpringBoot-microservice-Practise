package com.hasnat.grpcclientlocal.as_server;

import com.hasnat.proto.greetings.GreetingRequest;
import com.hasnat.proto.greetings.GreetingResponse;
import com.hasnat.proto.greetings.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    @Override
    public void greeting(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        String message = request.getMessage();
        System.out.println("Received Message: " + message);


        GreetingResponse greetingResponse = GreetingResponse.newBuilder()
                .setMessage("message: " + message + ". Message From gRPC client. ")
                .build();

        responseObserver.onNext(greetingResponse);
        responseObserver.onCompleted();
    }
}
