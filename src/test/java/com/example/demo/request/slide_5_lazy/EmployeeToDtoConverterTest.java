package com.example.demo.request.slide_5_lazy;

import com.example.demo.domain.Employee;
import com.example.demo.request.dao.EmployeeRepository;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("ALL")
@SpringBootTest
@WebAppConfiguration
public class EmployeeToDtoConverterTest {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    EmployeeToDtoConverter converter;

    void testEmployeeConverter() throws Exception {
        Employee employee = repository.findOne(1L);
        EmployeeDto employeeDto = converter.convert(employee);

        Assert.assertNotNull(employeeDto);
        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
        assertEquals(employee.getLastName(), employeeDto.getLastName());
        assertEquals(employee.getDepartment().getId(), employeeDto.getDepartmentId());
    }
}
