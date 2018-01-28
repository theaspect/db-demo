package com.example.demo.request.slide_3_where;

import com.example.demo.domain.Department;
import com.example.demo.domain.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;

public interface EmployeeSpringDao extends CrudRepository<Employee, Long> {

    Collection<Employee> findByDepartment(Department department);
    Collection<Employee> findByDepartmentAndBirthdayBetweenOrderByLastName(Department department, Date start, Date end);

}
