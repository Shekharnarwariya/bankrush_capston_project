package com.stackroute.service;


import com.stackroute.grpc.*;
import com.stackroute.repository.BankEmpRepository;
import com.stackroute.repository.BankRepository;
import com.stackroute.repository.BranchRepository;
import com.stackroute.repository.CustomerRepository;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;


@GrpcService
public class UserServiceServerImpl extends userServerGrpc.userServerImplBase {



    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BankEmpRepository bankEmpRepository;

    @Autowired
    private CustomerRepository customerRepository;



    @Override
    public void createCustomer(Customer request, StreamObserver<Customer> responseObserver) {

        long custId= Long.parseLong(Customer.newBuilder(request).getCustId());
        String name=Customer.newBuilder(request).getName();
        String emailId=Customer.newBuilder().getEmailId();
        String mobNo=Customer.newBuilder().getMobNo();
        String userName=Customer.newBuilder().getUserName();
        String password=Customer.newBuilder().getPassword();
        String streetAddress=Customer.newBuilder().getUserName();
        String city=Customer.newBuilder().getCity();
        String state=Customer.newBuilder().getState();
        String pincode=Customer.newBuilder().getPincode();


        com.stackroute.entity.Customer customerRequest=
                com.stackroute.entity.Customer.builder()
                        .custId(custId)
                        .name(name)
                        .emailId(emailId)
                        .mobNo(mobNo)
                        .username(userName)
                        .password(password)
                        .streetAddress(streetAddress)
                        .city(city)
                        .state(state)
                        .pincode(pincode)
                        .build();
        customerRepository.save(customerRequest);

        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }

    @Override
    public void createBank(Bank request, StreamObserver<Bank> responseObserver) {
        long bankId= Bank.newBuilder(request).getBankId();
        String bankName= Bank.newBuilder(request).getBankName();
        String username=Bank.newBuilder(request).getUsername();
        String password=Bank.newBuilder(request).getPassword();
        com.stackroute.entity.Bank bankRequest =
                com.stackroute.entity.Bank.builder()
                        .bankId(bankId)
                        .bankName(bankName)
                        .username(username)
                        .password(password)
                        .build();
        bankRepository.save(bankRequest);

        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }

    @Override
    public void createBankEmp(BankEmp request, StreamObserver<BankEmp> responseObserver) {

        long empId=BankEmp.newBuilder(request).getEmpId();
        String username=BankEmp.newBuilder(request).getUsername();
        String password=BankEmp.newBuilder(request).getPassword();
        String ifscCode=BankEmp.newBuilder(request).getIfscCode();

        com.stackroute.entity.BankEmp bankEmpRequest=
                com.stackroute.entity.BankEmp.builder()
                        .empId(empId)
                        .username(username)
                        .password(password)
                        .ifscCode(ifscCode)
                        .build();
        bankEmpRepository.save(bankEmpRequest);

        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }

    @Override
    public void createBranch(Branch request, StreamObserver<Branch> responseObserver) {
        String ifscCode=Branch.newBuilder().getIfscCode();
        long bankId=Branch.newBuilder().getBankId();
        String streetAddress=Branch.newBuilder().getStreetAddress();
        String city=Branch.newBuilder().getCity();
        String state=Branch.newBuilder().getState();
        String pincode=Branch.newBuilder().getPincode();

        com.stackroute.entity.Branch branchRequest=
                com.stackroute.entity.Branch.builder()
                        .ifscCode(ifscCode)
                        .bankId(bankId)
                        .streetAddress(streetAddress)
                        .city(city)
                        .state(state)
                        .pincode(pincode)
                        .build();


        //ifscCode,bankId,streetAddress,city,state,pincode);
        branchRepository.save(branchRequest);

        responseObserver.onNext(request);
        responseObserver.onCompleted();


    }

    @Override
    public void userLogin(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        System.out.println("Inside server");
        String username = request.getUsername();
        String password = request.getPassword();
        String role = request.getRole();
        String responseMessage;
        LoginResponse response;
        String token;
        System.out.println("Inside server"+username+" "+password+" "+role);
        if (role.equalsIgnoreCase("Customer")) {
            System.out.println("inside customer if");
            com.stackroute.entity.Customer customer=customerRepository.findByUsernameAndPassword(username,password);
            if (customer == null) {
                System.out.println("if null");
                responseMessage = "InvalidUser";
            }
            else{
                System.out.println(" not null");
                responseMessage="ValidUser";
            }

        } else if (role.equalsIgnoreCase("Bank")) {
            com.stackroute.entity.Bank bank = bankRepository.findByUsernameAndPassword(username, password);

            if (bank == null)
                responseMessage="InvalidUser";
            else
                responseMessage="ValidUser";

        } else if (role.equalsIgnoreCase("BankEmployee")) {
            com.stackroute.entity.BankEmp bankEmp;
            bankEmp = bankEmpRepository.findByUsernameAndPassword(username, password);

            if (bankEmp == null)
                responseMessage="InvalidUser";
            else
                responseMessage="ValidUser";

        }
        else {
            responseMessage="InvalidRole";
        }

        if(responseMessage.equals("ValidUser")) {
            token = Jwts.builder().setId(username).setSubject(role).signWith(SignatureAlgorithm.HS256, "mysecret").compact();
            response = LoginResponse.newBuilder().setResponseMessage(token).build();
        }

        else
        {  response = LoginResponse.newBuilder().setResponseMessage(responseMessage).build();}


        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
