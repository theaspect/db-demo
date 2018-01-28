package com.example.demo.request.slide_5_lazy;

import com.example.demo.domain.Department;
import com.example.demo.domain.Employee;
import com.example.demo.domain.Organisation;
import com.example.demo.request.dao.DepartmentRepository;
import com.example.demo.request.dao.EmployeeRepository;
import com.example.demo.request.dao.OrganisationRepository;
import com.example.demo.request.slide_7_report_x_case.ReportJdbcDao;
import com.example.demo.request.slide_7_report_x_case.ReportJpa;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SuppressWarnings("ALL")
@SpringBootTest
@WebAppConfiguration
public class EmployeeToDtoConverterTest {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    EmployeeToDtoConverter converter;

    @Autowired
    ReportJpa reportJpa;

    @Autowired
    ReportJdbcDao reportJdbcDao;

    @Test
    public void testEmployeeConverter() throws Exception {
        Collection<Employee> employees = repository.findAll();
        List<EmployeeDto> employeeDtos = employees.stream()
                .map(e -> converter.convert(e))
                .collect(Collectors.toList());

        Assert.assertTrue(employeeDtos.size() > 10);
    }

    @Test
    public void name() throws Exception {
        Iterable<Department> all = departmentRepository.findAll();
        System.out.println(all);
    }

    @Test
    public void getAllOrganisation() throws Exception {
        Collection<Organisation> all = organisationDao.findAllAndFetch();
        all.stream()
                .peek(o -> System.out.println(o))
                .flatMap(o -> o.getDepartments().stream())
                .peek(d -> System.out.println(d))
                .flatMap(d -> d.getEmployees().stream())
                .forEach(e -> System.out.println(e));
    }

    @Test
    public void report() throws Exception {
//        System.out.println(reportJpa.getReport());
        System.out.println(reportJdbcDao.getReport());
    }
}
