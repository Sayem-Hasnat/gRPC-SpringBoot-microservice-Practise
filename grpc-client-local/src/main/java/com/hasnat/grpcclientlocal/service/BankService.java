package com.hasnat.grpcclientlocal.service;

import com.hasnat.proto.bankservice.Balance;
import com.hasnat.proto.bankservice.BalanceRequest;
import com.hasnat.proto.bankservice.BankServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class BankService {
    @GrpcClient("grpc-server-local")
    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub; // blockingStub used for Uniray call

    public Balance getBalance(int accountNumber) {
        BalanceRequest balanceRequest = BalanceRequest.newBuilder()
                .setAccountNumber(accountNumber)
                .build();
        final Balance balance = this.bankServiceBlockingStub.getBalance(balanceRequest);
        System.out.println("balance" + balance);
        Balance balanceResponse = Balance.newBuilder().setAccountNumber(balance.getAccountNumber())
                .setAmount(balance.getAmount())
                .build();
        return balanceResponse;
    }
}
