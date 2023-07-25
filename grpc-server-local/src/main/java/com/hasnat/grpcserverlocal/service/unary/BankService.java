package com.hasnat.grpcserverlocal.service.unary;

import com.hasnat.grpcserverlocal.DB.BankAccountDB;
import com.hasnat.proto.bankservice.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BankService extends BankServiceGrpc.BankServiceImplBase {

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

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int withdrawalMoney = request.getRequestedAmount();
        int balance = BankAccountDB.getBalance(accountNumber);


        // gRPC error response Handling
        if (balance<withdrawalMoney){
            Status status = Status.FAILED_PRECONDITION
                    .withDescription("Not enough money, account have: "+ balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }
        for (int i = 0; i < (withdrawalMoney / 10); i++) {
              BankAccountDB.deductBalance(accountNumber, 10);
            Money money  = Money.newBuilder()
                    .setWithdrawalMoney(withdrawalMoney)
                    .setAvailableBalance(BankAccountDB.getBalance(request.getAccountNumber()))
                    .build();
            responseObserver.onNext(money);

        }
        responseObserver.onCompleted();
    }
}






