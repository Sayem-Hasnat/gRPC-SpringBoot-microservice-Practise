package com.hasnat.grpcserverlocal.configuration;

import com.hasnat.grpcserverlocal.service.unary.BankService;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLException;
import java.io.File;
import java.security.interfaces.RSAKey;

@Configuration
public class GrpcClientConfig {
    @Value("${grpc.server.host}")
    private String grpcServerHost;

    @Value("${grpc.server.port}")
    private int grpcServerPort;

    @Value("${grpc.server.security.certificateChain}")
    private String certChainPath; // Path to client.crt

    @Value("${grpc.server.security.privateKey}")
    private String privateKeyPath; // Path to client.key

    @Bean
    public Server grpcChannel() throws SSLException {
        SslContext sslContext;
        try {
            sslContext = GrpcSslContexts.configure(
                    SslContextBuilder.forServer(
                             new File("/home/hasnat/hasnat/projects/practise/grpc with Spring boot microservice practise/ssl-tsl/server.crt"),
                             new File("/home/hasnat/hasnat/projects/practise/grpc with Spring boot microservice practise/ssl-tsl/server.key")
                    )
            ).build();
        } catch (SSLException e) {
            throw new RuntimeException(e);
        }
        Server server = NettyServerBuilder.forPort(grpcServerPort)
                .sslContext(sslContext)
                .addService(new BankService())
                .build();

        System.out.println("Created Server: " + server);
        return server;
    }
}


/*
        try {
            File certChain = new File(certChainPath);
            File privateKey = new File(privateKeyPath);

            return NettyChannelBuilder.forAddress(grpcServerHost, grpcServerPort)
                    .useTransportSecurity()
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create gRPC channel.", e);
        }*/