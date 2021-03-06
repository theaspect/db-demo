package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private Date birthday;

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "account")
    private Set<Client> clients = new HashSet<>();

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
