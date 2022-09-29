package com.stackroute.grpc.servertwo.rest.controller;
import com.stackroute.grpc.Bank;
import com.stackroute.grpc.BankEmp;
import com.stackroute.grpc.Branch;
import com.stackroute.grpc.Customer;
import com.stackroute.grpc.servertwo.client.GRPCClientService;
import com.stackroute.grpc.servertwo.rest.dto.BankClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.BankEmpClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.BranchClientRequest;
import com.stackroute.grpc.servertwo.rest.dto.CustomerClientRequest;
import com.stackroute.grpc.servertwo.rest.exception.InvalidCredentialsException;
import com.stackroute.grpc.servertwo.rest.exception.InvalidRoleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ClientController {

    @Autowired
    private final GRPCClientService gRPCClientService;


    private final static Logger log = LoggerFactory.getLogger(ClientController.class);

    public ClientController(GRPCClientService gRPCClientService) {
        this.gRPCClientService=gRPCClientService;
    }

    @PostMapping("/registration/customer")
    public CustomerClientRequest createCustomer(@RequestBody CustomerClientRequest customerClientRequest) {

        Customer customer=gRPCClientService.saveCustomer(customerClientRequest);
        return CustomerClientRequest.builder()
                .custId(customerClientRequest.getCustId())
                .name(customerClientRequest.getName())
                .emailId(customerClientRequest.getEmailId())
                .mobNo(customerClientRequest.getMobNo())
                .userName(customerClientRequest.getUserName())
                .password(customerClientRequest.getPassword())
                .streetAddress(customerClientRequest.getStreetAddress())
                .city(customerClientRequest.getCity())
                .state(customerClientRequest.getState())
                .pincode(customerClientRequest.getPincode())
                .build();
    }

    @PostMapping("/registration/bank")
    public BankClientRequest createBank(@RequestBody BankClientRequest bankClientRequest){

        Bank bank=gRPCClientService.saveBank(bankClientRequest);
        return BankClientRequest.builder()
                .bankId(bankClientRequest.getBankId())
                .bankName(bankClientRequest.getBankName())
                .username(bankClientRequest.getUsername())
                .password(bankClientRequest.getPassword())
                .build();
    }

    @PostMapping("/registration/bankEmp")
    public BankEmpClientRequest createBankEmp(@RequestBody BankEmpClientRequest bankEmpClientRequest){

        BankEmp bankEmp=gRPCClientService.saveBankEmp(bankEmpClientRequest);
        return BankEmpClientRequest.builder()
                .empId(bankEmpClientRequest.getEmpId())
                .username(bankEmpClientRequest.getUsername())
                .password(bankEmpClientRequest.getPassword())
                .ifscCode(bankEmpClientRequest.getIfscCode())
                .build();
    }


    @PostMapping("/registration/branch")
    public BranchClientRequest createBranch(@RequestBody BranchClientRequest branchClientRequest){

        Branch branch=gRPCClientService.saveBranch(branchClientRequest);
        return BranchClientRequest.builder()
                .ifscCode(branchClientRequest.getIfscCode())
                .bankId(branchClientRequest.getBankId())
                .streetAddress(branchClientRequest.getStreetAddress())
                .city(branchClientRequest.getCity())
                .state(branchClientRequest.getState())
                .pincode(branchClientRequest.getPincode())
                .build();
    }

    @GetMapping("/login/{username}/{password}/{role}")
    public ResponseEntity<?> loginUser(@PathVariable String username, @PathVariable String password, @PathVariable String role) throws InvalidRoleException, InvalidCredentialsException {
        String result;
        result= gRPCClientService.generateToken(username,password,role);
        if(result.equals("InvalidRole"))
            throw new InvalidRoleException();
        else if(result.equals("InvalidUser"))
            throw new InvalidCredentialsException();

        return new ResponseEntity<>("Logged in , Token :"+result, HttpStatus.OK);

    }

    @PutMapping(value = "/update/bank")
    public ResponseEntity<?> UpdateBank(@RequestBody BankClientRequest bankClientRequest) {
        String successMessage="";
         gRPCClientService.UpdateBank(bankClientRequest);


        //String successMessage = environment.getProperty("API.UPDATE_SUCCESS");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PutMapping(value = "/Update/customer")
    public ResponseEntity<?> UpdateCustomer(@RequestBody CustomerClientRequest customerClientRequest) {
        String successMessage="";
        gRPCClientService.UpdateCustomer(customerClientRequest);


        //String successMessage = environment.getProperty("API.UPDATE_SUCCESS");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
    @PutMapping(value = "/update/branch")
    public ResponseEntity<?> UpdateBranch(@RequestBody BranchClientRequest branchClientRequest) {
        String successMessage="";
        gRPCClientService.UpdateBranch(branchClientRequest);


        //String successMessage = environment.getProperty("API.UPDATE_SUCCESS");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
    @PutMapping(value = "/update/bankEmp")
    public ResponseEntity<?> UpdateBankEmp(@RequestBody BankEmpClientRequest bankEmpClientRequest) {
        String successMessage="";
        gRPCClientService.UpdateBankEmp(bankEmpClientRequest);


        //String successMessage = environment.getProperty("API.UPDATE_SUCCESS");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

}
