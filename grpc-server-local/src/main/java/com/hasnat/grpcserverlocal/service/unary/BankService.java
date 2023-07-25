package com.hasnat.grpcserverlocal.service.unary;

import com.hasnat.grpcserverlocal.DB.BankAccountDB;
import com.hasnat.proto.bankservice.Balance;
import com.hasnat.proto.bankservice.BalanceRequest;
import com.hasnat.proto.bankservice.BankServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BankService  extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceRequest balanceRequest, StreamObserver<Balance> responseObserver) {
        int accountNumber = balanceRequest.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(BankAccountDB.getBalance(accountNumber))
                .setAccountNumber(accountNumber)
                        .build();

        BankAccountDB.printAccountDetails();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }
}
