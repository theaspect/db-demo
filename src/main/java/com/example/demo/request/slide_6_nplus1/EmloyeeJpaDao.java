package com.example.demo.request.slide_6_nplus1;

import com.example.demo.SqlLogFilter;
import com.example.demo.domain.Client;
import com.example.demo.domain.Department;
import com.example.demo.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
@Component
public class EmloyeeJpaDao {
    private static final Logger log = LoggerFactory.getLogger(EmloyeeJpaDao.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * Simulate N+1
     */
    @Transactional
    public Map<Long, Long> getContractsByEmployee() {
        return SqlLogFilter.withCounter(() -> {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
            List<Employee> resultList = query.getResultList();
            log.info("RESULT COUNT {}", resultList.size());

            return resultList.stream().collect(Collectors.toMap(
                    Employee::getId,
                    (Employee e) -> e.getClients().stream().map(Client::getContracts).count()));
        });
    }

    @Transactional
    public Map<Long, Long> getContractsByEmployeeEager() {
        return SqlLogFilter.withCounter(() -> {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e LEFT JOIN FETCH e.clients c", Employee.class);
            List<Employee> resultList = query.getResultList();
            log.info("RESULT COUNT {}", resultList.size());

            return resultList.stream().collect(Collectors.toMap(
                    Employee::getId,
                    (Employee e) -> e.getClients().stream().map(Client::getContracts).count(),
                    Long::sum));
        });
    }

    @Transactional
    public Map<Long, Long> getContractsByEmployeeNotSoEagerest() {
        return SqlLogFilter.withCounter(() -> {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e LEFT JOIN FETCH e.clients c LEFT JOIN FETCH c.contracts cs", Employee.class);
            List<Employee> resultList = query.getResultList();
            log.info("RESULT COUNT {}", resultList.size());

            return resultList.stream().collect(Collectors.toMap(
                    Employee::getId,
                    (Employee e) -> e.getClients().stream().map(Client::getContracts).count(),
                    Long::sum));
        });
    }

    @Transactional
    public Map<Long, Long> getContractsByEmployeeEagerest() {
        return SqlLogFilter.withCounter(() -> {
            List<Department> pracache = em.createQuery("SELECT d FROM Department d LEFT JOIN FETCH d.organisation o", Department.class).getResultList();
            List<Employee> resultList = em.createQuery("SELECT e FROM Employee e LEFT JOIN FETCH e.clients c LEFT JOIN c.contracts cn", Employee.class).getResultList();
            log.info("RESULT COUNT {}", resultList.size());

            return resultList.stream().collect(Collectors.toMap(
                    Employee::getId,
                    (Employee e) -> e.getClients().stream().map(Client::getContracts).count(),
                    Long::sum));
        });
    }
}
