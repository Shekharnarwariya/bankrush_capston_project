package com.stackroute.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@Builder
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "cust_id")
    private long custId;
    @Column(name = "name")
    private String name;
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "mob_no")
    private String mobNo;
    @Column(name = "user_name")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "pincode")
    private String pincode;



}
