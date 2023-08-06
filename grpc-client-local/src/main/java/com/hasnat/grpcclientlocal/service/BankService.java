package com.hasnat.grpcclientlocal.service;

import com.hasnat.grpcclientlocal.configuration.ManagedChannelConfiguration;
import com.hasnat.grpcclientlocal.dto.BalanceResponse;
import com.hasnat.grpcclientlocal.dto.ClientWithdrawRequest;
import com.hasnat.grpcclientlocal.dto.MoneyStreamingResponse;
import com.hasnat.proto.bankservice.Balance;
import com.hasnat.proto.bankservice.BalanceRequest;
import com.hasnat.proto.bankservice.BankServiceGrpc;
import com.hasnat.proto.bankservice.WithdrawRequest;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;

@Service
public class BankService {
    @GrpcClient("grpc-server-local")
    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub; // blockingStub used for Uniray call
    @GrpcClient("grpc-server-local")
    private BankServiceGrpc.BankServiceStub bankServiceStub; // blockingStub used for async streaming call

    @Autowired
    private ManagedChannelConfiguration managedChannelConfiguration;



    //check unary RPC
    public BalanceResponse getBalance(int accountNumber) {
        BalanceRequest balanceRequest = BalanceRequest.newBuilder()
                .setAccountNumber(accountNumber)
                .build();
        bankServiceBlockingStub  = BankServiceGrpc.newBlockingStub(managedChannelConfiguration.getManagedChannel());
        final Balance balance = bankServiceBlockingStub.getBalance(balanceRequest);
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
