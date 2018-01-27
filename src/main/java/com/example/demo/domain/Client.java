package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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

    @ManyToOne
    private Employee account;

    @OneToMany(mappedBy = "client")
    private List<Contract> contracts;
}
