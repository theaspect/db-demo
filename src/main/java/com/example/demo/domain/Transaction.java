package com.example.demo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private Date date;
    private Long amount;

    @ManyToOne
    private Contract contract;
}
