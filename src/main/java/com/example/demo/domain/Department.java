package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
// Break JSON recursion
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Department {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    //@OneToMany(fetch = FetchType.EAGER) // ???
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Employee> employees;

    @ManyToOne
    private Organisation organisation;
}
