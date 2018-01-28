package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data

@ToString(exclude = "transactions")
@EqualsAndHashCode(exclude = "transactions")

@Entity
public class Contract {
    @Id
    @GeneratedValue
    private Long id;

    private Date dateStart;
    private Date dateEnd;

    private Long amount;

    @ManyToOne
    private Client client;

//    @ManyToOne
//    private Employee account;

    @OneToMany(mappedBy = "contract")
    private List<Transaction> transactions = new ArrayList<>();
}
