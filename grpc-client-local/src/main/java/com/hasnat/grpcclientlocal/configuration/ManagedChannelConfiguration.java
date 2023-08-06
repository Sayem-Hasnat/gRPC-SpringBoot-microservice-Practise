package com.hasnat.grpcclientlocal.configuration;

import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import java.io.File;

@Configuration
public class ManagedChannelConfiguration {

    SslContext sslContext;
    ManagedChannel managedChannel;

    {
        try {
            sslContext = GrpcSslContexts.forClient()
                    .trustManager(new File("/home/hasnat/hasnat/projects/practise/grpc with Spring boot microservice practise/ssl-tsl/server.cert"))
                    .build();
            System.out.println("Cert file "+ sslContext.attributes());
            System.out.println("isServer file "+ sslContext.isServer());
            System.out.println("isClient file "+ sslContext.isClient());
        } catch (SSLException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public ManagedChannel getManagedChannel() {
        ManagedChannel managedChannel = NettyChannelBuilder.forAddress("127.0.0.1", 8085)
                .sslContext(sslContext)
                .negotiationType(NegotiationType.TLS)
                .build();
        System.out.println("Managed Channel "+ managedChannel);
        return managedChannel;
    }


}
