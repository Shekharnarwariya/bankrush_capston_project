package com.stackroute.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.*;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@ToString
@Builder
@Table(name = "branch")
public class Branch {

    @Id
    @Column(name="ifsc_Code")
    private String ifscCode;
    @Column(name="bank_id")
    private long bankId;
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "pincode")
    private String pincode;


}
