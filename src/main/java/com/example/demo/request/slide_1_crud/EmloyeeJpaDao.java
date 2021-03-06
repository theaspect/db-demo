package com.example.demo.request.slide_1_crud;

import com.example.demo.domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@SuppressWarnings("ALL")
public class EmloyeeJpaDao {

    @PersistenceContext
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

    public void delete(Integer id) {
        Employee emp = (Employee) em.find(Employee.class, id);
        em.remove(emp);
    }
}
