package com.example.demo.request.slide_4_projection;

import com.example.demo.domain.Department;
import com.example.demo.domain.Employee;
import com.example.demo.dto.ShortEmployeeDto;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

@SuppressWarnings("ALL")
@Component
public class EmloyeeJpaDao4 {

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

    public Collection<Employee> getByDepartment(Department department) {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.department.id = :departmentId");
        query.setParameter("departmentId", department.getId());
        return (Collection<Employee>) query.getResultList();
    }

    public Collection<ShortEmployeeDto> getCriteriaShortEmployeeByDepartmentId(Long departmentId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ShortEmployeeDto> cq = cb.createQuery(ShortEmployeeDto.class);
        Root<Employee> cr = cq.from(Employee.class);
        cq
                .where(cb.equal(cr.get("department").get("id"), departmentId))
                .multiselect(cr.get("id"), cr.get("firstName"));
        return em.createQuery(cq).getResultList();
    }

    public Collection<ShortEmployeeDto> getShortEmployeeByDepartmentId(Long departmentId) {
        TypedQuery<ShortEmployeeDto> query = em.createQuery(
                "SELECT new com.example.demo.dto.ShortEmployeeDto" +
                        "(e.id, e.firstName) " +
                "FROM Employee e WHERE e.department.id = :departmentId",
                ShortEmployeeDto.class);
        query.setParameter("departmentId", departmentId);
        return query.getResultList();
    }

    public void delete(Integer id) {
        Employee emp = (Employee) em.find(Employee.class, id);
        em.remove(emp);
    }
}
