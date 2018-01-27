package com.example.demo.request.slide_4_projection;

import com.example.demo.domain.Department;
import com.example.demo.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;

@SuppressWarnings("ALL")
public class EmloyeeJpaDao {

    @Autowired
    private EntityManager em;

    public void save(Employee employee) {
        em.persist(employee);
    }

    public void update(Employee employee) {
        em.merge(employee);
    }

    public Employee getById(Integer id) {
        return (Employee) em.find(Employee.class, id);
    }

    public Collection<Employee> getAll() {
        Query query = em.createQuery("SELECT e FROM Employee e");
        return (Collection<Employee>) query.getResultList();
    }

    public Collection<Employee> getByDepartment(Department department) {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.department.id = :departmentId");
        query.setParameter("departmentId", department.getId());
        return (Collection<Employee>) query.getResultList();
    }

    public void delete(Integer id) {
        Employee emp = (Employee) em.find(Employee.class, id);
        em.remove(emp);
    }
}
