package com.example.demo.request.slide_5_lazy;

import com.example.demo.domain.Department;
import com.example.demo.domain.Employee;
import com.example.demo.domain.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Collection;

@SuppressWarnings("ALL")
@Repository
public class OrganisationDao {

    @Autowired
    private EntityManager entityManager;

    public Collection<Organisation> findAllAndFetch() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Organisation> criteriaQuery = criteriaBuilder.createQuery(Organisation.class);
        Root<Organisation> root = criteriaQuery.from(Organisation.class);
        criteriaQuery.distinct(true);

        Join<Organisation, Department> departmentJoin = (Join) root.fetch("departments", JoinType.LEFT);
        Join<Department, Employee> employeeJoin = (Join) departmentJoin.fetch("employees", JoinType.LEFT);

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")),
                criteriaBuilder.asc(departmentJoin.get("id")),
                criteriaBuilder.asc(employeeJoin.get("id")));

        TypedQuery<Organisation> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

}
