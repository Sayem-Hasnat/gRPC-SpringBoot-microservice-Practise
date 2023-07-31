package com.hasnat.grpcclientlocal.service;

import com.hasnat.grpcclientlocal.dto.BalanceResponse;
import com.hasnat.grpcclientlocal.dto.ClientWithdrawRequest;
import com.hasnat.grpcclientlocal.dto.MoneyStreamingResponse;
import com.hasnat.proto.bankservice.Balance;
import com.hasnat.proto.bankservice.BalanceRequest;
import com.hasnat.proto.bankservice.BankServiceGrpc;
import com.hasnat.proto.bankservice.WithdrawRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;

@Service
public class BankService {
    @GrpcClient("grpc-server-local")
    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub; // blockingStub used for Uniray call
    @GrpcClient("grpc-server-local")
    private BankServiceGrpc.BankServiceStub bankServiceStub; // blockingStub used for async streaming call

    //check unary RPC
    public BalanceResponse getBalance(int accountNumber) {
        BalanceRequest balanceRequest = BalanceRequest.newBuilder()
                .setAccountNumber(accountNumber)
                .build();
        final Balance balance = this.bankServiceBlockingStub.getBalance(balanceRequest);
        System.out.println("balance" + balance);
        BalanceResponse balanceResponse = new BalanceResponse(balance.getAccountNumber(),balance.getAmount());
        return balanceResponse;
    }

    public void withdrawRequest(ClientWithdrawRequest clientWithdrawRequest){

        WithdrawRequest withdrawRequestToServer =
                WithdrawRequest.newBuilder()
                        .setAccountNumber(clientWithdrawRequest.getAccountNumber())
                        .setRequestedAmount(clientWithdrawRequest.getAmount())
                        .build();
        this.bankServiceBlockingStub.withdraw(withdrawRequestToServer)
                .forEachRemaining(m -> System.out.println(m + "localTime "+ LocalTime.now()));
        CountDownLatch latch = new CountDownLatch(1);
        this.bankServiceStub.withdraw(withdrawRequestToServer, new MoneyStreamingResponse());


    }


}
