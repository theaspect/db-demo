package com.example.demo.request.slide_1_crud;

import com.example.demo.domain.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeSpringDao extends CrudRepository<Employee, Long> {
}
