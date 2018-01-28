package com.example.demo.request.slide_4_projection;

import com.example.demo.domain.Department;
import com.example.demo.domain.Employee;
import com.example.demo.dto.ShortEmployeeDto;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
public interface EmployeeSpringDao extends CrudRepository<Employee, Long> {

    Collection<Employee> findByDepartment(Department department);

    Collection<ShortEmployeeDto> findCustomEmployeeByDepartment(Department department);

}
