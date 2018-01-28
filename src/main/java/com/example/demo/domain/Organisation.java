package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@ToString(exclude = "departments")
@EqualsAndHashCode(exclude = "departments")

@Entity
public class Organisation {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "organisation", fetch = FetchType.LAZY)
    private Set<Department> departments;

}