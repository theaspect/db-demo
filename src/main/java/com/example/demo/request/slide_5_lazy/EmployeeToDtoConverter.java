package com.example.demo.request.slide_5_lazy;

import com.example.demo.domain.Employee;
import org.springframework.stereotype.Component;

@SuppressWarnings("ALL")
@Component
public class EmployeeToDtoConverter {

    public EmployeeDto convert(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());

        return dto;
    }



}
