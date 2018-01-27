package com.example.demo.request.dao;

import com.example.demo.domain.Department;
import com.example.demo.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Based on https://spring.io/guides/gs/relational-data-access/
 */
@Component
public class JpaRequest {
    private static final Logger log = LoggerFactory.getLogger(JpaRequest.class);

    private final DepartmentRepository departmentRepository;
    private final EntityManager entityManager;

    @Autowired
    public JpaRequest(DepartmentRepository departmentRepository, EntityManager entityManager) {
        this.departmentRepository = departmentRepository;
        this.entityManager = entityManager;
    }

    public List<Department> getDepartments() {
        log.info("Request all departments");
        return StreamSupport.stream(departmentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployees() {
        log.info("Request all employees");
        return entityManager.createQuery("from Employee", Employee.class).getResultList();
    }

    public List<Department> getDepartmentsByManager(Long id) {
        log.info("Request departments for {}", id);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Department> q = cb.createQuery(Department.class);
        Root<Department> c = q.from(Department.class);
        ParameterExpression<Long> p = cb.parameter(Long.class, "id");
        CriteriaQuery<Department> where = q.select(c).where(cb.equal(c.get("id"), p));

        return entityManager.createQuery(where).setParameter("id", id).getResultList();
    }

    public Employee getManagerByDepartment(Long id) {
        log.info("Request departments for {}", id);

        // Native query doesn't support generic
        return (Employee) entityManager.createNativeQuery("" +
                "SELECT * " +
                "FROM employee e JOIN department d on d.manager_id = e.id " +
                "WHERE d.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
