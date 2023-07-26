package com.hasnat.grpcclientlocal.dto;

import com.hasnat.proto.bankservice.Money;
import io.grpc.stub.StreamObserver;

public class MoneyStreamingResponse implements StreamObserver<Money> {
    @Override
    public void onNext(Money money) {
        System.out.printf("Received Async: "+ money);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(
                throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("Done!!!!!!!!!");
    }
}
