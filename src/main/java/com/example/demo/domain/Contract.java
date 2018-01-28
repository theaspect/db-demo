package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Contract {
    @Id
    @GeneratedValue
    private Long id;

    private Date dateStart;
    private Date dateEnd;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Employee account;

    @OneToMany(mappedBy = "contract")
    private Set<Transaction> transactions = new HashSet<>();
}
