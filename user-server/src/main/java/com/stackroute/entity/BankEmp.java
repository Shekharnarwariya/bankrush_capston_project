package com.stackroute.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Builder
@ToString
@Table(name = "bank_emp")
public class BankEmp {

    @Id
    @Column(name="emp_id")
    private long empId;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="ifsc_code")
    private String ifscCode;
}
