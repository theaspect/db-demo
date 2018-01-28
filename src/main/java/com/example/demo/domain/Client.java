package com.example.demo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Data

@ToString(exclude = "contracts")
@EqualsAndHashCode(exclude = "contracts")

@Entity
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String street;
    private String city;
    private String state;
    private String zipcode;

//    @ManyToOne
//    private Employee account;

    @OneToMany(mappedBy = "client")
    private Set<Contract> contracts;
}
