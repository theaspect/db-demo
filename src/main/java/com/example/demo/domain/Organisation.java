package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Organisation {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    //@OneToMany(fetch = FetchType.EAGER)
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Department> departments = new HashSet<>();
}
