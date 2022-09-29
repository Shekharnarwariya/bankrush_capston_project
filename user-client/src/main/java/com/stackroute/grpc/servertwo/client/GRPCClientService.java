package com.stackroute.grpc.servertwo.client;

import com.stackroute.grpc.servertwo.rest.dto.BankClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.BankEmpClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.BranchClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.CustomerClientRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import com.stackroute.grpc.*;


@Service
public class GRPCClientService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;


    private final static Logger log = LoggerFactory.getLogger(GRPCClientService.class);


    public Customer saveCustomer(CustomerClientRequest request) {
        log.info("### Start execute GRPCClientService from server two");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        Customer customer = null;
        try {
            //log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
            // ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
            userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);

            Customer customerBuilder = Customer.newBuilder()
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
                    .build();

            customer = stub.createCustomer(customerBuilder);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            channel.shutdown();
        }
        return customer;
    }


    public Bank saveBank(BankClientRequest request) {
        log.info("### Start execute GRPCClientService from server two");
        // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        Bank bank = null;
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        try {
            bank = stub.createBank(Bank.newBuilder()
                    .setBankId(request.getBankId())
                    .setBankName(request.getBankName())
                    .setUsername(request.getUsername())
                    .setPassword(request.getPassword())
                    .build());

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            channel.shutdown();
        }
        return bank;


    }


    public BankEmp saveBankEmp(BankEmpClientRequest request) {
        //log.info("### Start execute GRPCClientService from server two");
        // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        BankEmp bankEmp = null;
        try {
            bankEmp = stub.createBankEmp(BankEmp.newBuilder()
                    .setEmpId(request.getEmpId())
                    .setUsername(request.getUsername())
                    .setPassword(request.getPassword())
                    .setIfscCode(request.getIfscCode())
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());

        } finally {
            channel.shutdown();
        }
        return bankEmp;
    }

    public Branch saveBranch(BranchClientRequest request) {
        log.info("### Start execute GRPCClientService from server two");
        //log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        Branch branch = null;
        try {
            branch = stub.createBranch(Branch.newBuilder()
                    .setIfscCode(request.getIfscCode())
                    .setBankId(request.getBankId())
                    .setStreetAddress(request.getStreetAddress())
                    .setCity(request.getCity())
                    .setState(request.getState())
                    .setPincode(request.getPincode())
                    .build());
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            channel.shutdown();
        }
        return branch;

    }


    public String UpdateBranch(BranchClientRequest request) {
        log.info("### Start execute GRPCClientService from server two");
        //log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        UpdateResponse updateResponse = null;
        try {

            updateResponse = stub.updateBranchDetails(Branch.newBuilder()
                    .setIfscCode(request.getIfscCode())
                    .setBankId(request.getBankId())
                    .setStreetAddress(request.getStreetAddress())
                    .setCity(request.getCity())
                    .setState(request.getState())
                    .setPincode(request.getPincode())
                    .build());

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            channel.shutdown();
        }
        return updateResponse.getResponseMessage();


    }

    public String UpdateBank(BankClientRequest request) {

        log.info("### Start execute GRPCClientService from server two");
        // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        UpdateResponse updateResponse = null;
        try {

            updateResponse = stub.updateBankPassword(Bank.newBuilder()
                    .setBankId(request.getBankId())
                    .setBankName(request.getBankName())
                    .setUsername(request.getUsername())
                    .setPassword(request.getPassword())
                    .build());

            return updateResponse.getResponseMessage();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            channel.shutdown();
        }
        return updateResponse.getResponseMessage();
    }

    public String UpdateCustomer(CustomerClientRequest request) {

        log.info("### Start execute GRPCClientService from server two");
        // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        UpdateResponse updateResponse = null;

        try {
            updateResponse = stub.updateCustomerDetails(Customer.newBuilder()
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


        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            channel.shutdown();
        }
        return updateResponse.getResponseMessage();
    }

    public String UpdateBankEmp(BankEmpClientRequest request) {

        log.info("### Start execute GRPCClientService from server two");
        // log.info(String.format("## grpcServerOneHost::%s and grpcServerPort::%s", grpcServerOneHost, grpcServerOnePort));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        UpdateResponse updateResponse = null;

        try {
            updateResponse = stub.updateBanKEmpPassword(BankEmp.newBuilder()
                    .setEmpId(request.getEmpId())
                    .setUsername(request.getUsername())
                    .setPassword(request.getPassword())
                    .setIfscCode(request.getIfscCode())
                    .build());
            return updateResponse.getResponseMessage();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            channel.shutdown();
        }
        return updateResponse.getResponseMessage();
    }
    public String generateToken(String username,String password,String role) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();
        userServerGrpc.userServerBlockingStub stub = userServerGrpc.newBlockingStub(channel);
        LoginResponse response = stub.userLogin(LoginRequest.newBuilder().setUsername(username).setPassword(password).setRole(role).build());
        Jws<Claims> result= Jwts.parser().setSigningKey("mysecret").parseClaimsJws(response.getResponseMessage());
        channel.shutdown();
        String email = result.getBody().get("email",String.class);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("BankRush- Login");
        mailMessage.setText("You have successfully logged in to BankRush!!");
        mailMessage.setFrom("bankrush.app@gmail.com");
     //   JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.send(mailMessage);

        return response.getResponseMessage();

    }
}
