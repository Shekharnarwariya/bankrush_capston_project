package com.stackroute.grpc.servertwo.client;

import com.stackroute.grpc.servertwo.rest.dto.BankClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.BankEmpClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.BranchClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.CustomerClientRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stackroute.grpc.*;







@Service
public class GRPCClientService  {
//
//    @Value("${grpcOne.port:9090}")
//    private int grpcServerOnePort;
//
//    @Value("${grpcOne.host:localhost}")
//    private String grpcServerOneHost;



    private final static Logger log = LoggerFactory.getLogger(GRPCClientService.class);

    public Customer saveCustomer(CustomerClientRequest request) {
        log.info("### Start execute GRPCClientService from server two");
        //log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        //ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerOneHost, grpcServerOnePort).usePlaintext().build();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub= userServerGrpc.newBlockingStub(channel);

        Customer customer=stub.createCustomer(Customer.newBuilder()
                .setCustId(request.getCustId())
                .setName(request.getName())
                .setEmailId(request.getEmailId())
                .setMobNo(request.getMobNo())
                .setUserName(request.getUserName())
                .setPassword(request.getPassword())
                .setStreetAddress(request.getStreetAddress())
                .setCity(request.getCity())
                .setState(request.getState())
                .setPincode(request.getPincode())
                .build());
        channel.shutdown();

        return customer;
    }


    public Bank saveBank(BankClientRequest request){
        log.info("### Start execute GRPCClientService from server two");
       // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        //ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerOneHost, grpcServerOnePort).usePlaintext().build();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub= userServerGrpc.newBlockingStub(channel);


        Bank bank=stub.createBank(Bank.newBuilder()
                .setBankId(request.getBankId())
                .setBankName(request.getBankName())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .build());
        channel.shutdown();

        return bank;

    }


    public BankEmp saveBankEmp(BankEmpClientRequest request)
    {
        //log.info("### Start execute GRPCClientService from server two");
       // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
       // ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerOneHost, grpcServerOnePort).usePlaintext().build();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub= userServerGrpc.newBlockingStub(channel);

        BankEmp bankEmp=stub.createBankEmp(BankEmp.newBuilder()
                .setEmpId(request.getEmpId())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setIfscCode(request.getIfscCode())
                .build());
        channel.shutdown();

        return bankEmp;

    }

    public  Branch saveBranch(BranchClientRequest request){
        log.info("### Start execute GRPCClientService from server two");
        //log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
       // ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerOneHost, grpcServerOnePort).usePlaintext().build();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub= userServerGrpc.newBlockingStub(channel);

        Branch branch=stub.createBranch(Branch.newBuilder()
                .setIfscCode(request.getIfscCode())
                .setBankId(request.getBankId())
                .setStreetAddress(request.getStreetAddress())
                .setCity(request.getCity())
                .setState(request.getState())
                .setPincode(request.getPincode())
                .build());
        channel.shutdown();
        return branch;
    }

}
