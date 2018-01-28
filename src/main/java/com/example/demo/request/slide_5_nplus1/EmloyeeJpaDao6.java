package com.example.demo.request.slide_5_nplus1;

import com.example.demo.SqlLogFilter;
import com.example.demo.domain.Client;
import com.example.demo.domain.Contract;
import com.example.demo.domain.Department;
import com.example.demo.domain.Employee;
import com.example.demo.dto.SankiDto;
import com.example.demo.request.slide_4_projection.ContractSpringDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings({"Duplicates", "unchecked"})
@Component
public class EmloyeeJpaDao6 {
    private static final Logger log = LoggerFactory.getLogger(EmloyeeJpaDao6.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ContractSpringDao contractDao;

    public Collection<Employee> getAll() {
        Query query = em.createQuery("FROM Employee e");
        return (Collection<Employee>) query.getResultList();
    }

    @Transactional
    public Map<Long, Long> getContractsByEmployeeAgg() {
        return SqlLogFilter.withCounter(() -> {
            Query query = em.createQuery(
                    "SELECT e.id as id, COUNT(ct) as count " +
                            "FROM Employee e LEFT JOIN e.clients c " +
                            "LEFT JOIN c.contracts ct GROUP BY e.id");
            Map<Long, Long> result = ((List<Object[]>) query.getResultList())
                    .stream()
                    .collect(Collectors.toMap(
                            r -> (Long) r[0],
                            r -> (Long) r[1]));
            log.info("RESULT COUNT {}", result.size());
            return result;
        });
    }

    /**
     * Simulate N+1
     */
    @Transactional
    public Map<Long, Long> getContractsByEmployee() {
        return SqlLogFilter.withCounter(() -> {
            TypedQuery<Employee> query = em.createQuery(
                    "SELECT e FROM Employee e", Employee.class);
            List<Employee> resultList = query.getResultList();
            log.info("RESULT COUNT {}", resultList.size());

            return resultList.stream().collect(Collectors.toMap(
                    Employee::getId,
                    (Employee e) -> e.getClients().stream()
                            .map(Client::getContracts).count()));
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
            TypedQuery<Employee> query = em.createQuery(
                    "SELECT e FROM Employee e " +
                            "LEFT JOIN FETCH e.clients c " +
                            "LEFT JOIN FETCH c.contracts cs", Employee.class);
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
            em.createQuery("SELECT d FROM Department d " +
                            "LEFT JOIN FETCH d.organization o",
                    Department.class).getResultList();
            List<Employee> resultList = em.createQuery(
                    "SELECT e FROM Employee e " +
                            "LEFT JOIN FETCH e.clients c " +
                            "LEFT JOIN c.contracts cn",
                    Employee.class).getResultList();
            log.info("RESULT COUNT {}", resultList.size());

            return resultList.stream().collect(Collectors.toMap(
                    Employee::getId,
                    (Employee e) -> e.getClients().stream()
                            .map(Client::getContracts).count(),
                    Long::sum));
        });
    }

    public List<SankiDto> getSankiReport() {
        CompletableFuture<List<SankiDto>> clientToContract =
                CompletableFuture.supplyAsync(
                        () -> StreamSupport.stream(contractDao.findAll().spliterator(), false)
                                .peek(EmloyeeJpaDao6::logAndSleep)
                                .map(c -> new SankiDto(
                                        c.getClient().getName(), c.getAmount(), c.getContractType().toString()))
                                .collect(Collectors.toList())
                );

        CompletableFuture<List<SankiDto>> contractToEmployee =
                CompletableFuture.supplyAsync(
                        () -> StreamSupport.stream(contractDao.findAll().spliterator(), false)
                                .peek(EmloyeeJpaDao6::logAndSleep)
                                .map(c -> new SankiDto(c.getContractType().toString(), c.getAmount(),
                                        c.getAccount().getFullName()))
                                .collect(Collectors.toList())
                );

        return Stream.of(clientToContract, contractToEmployee).reduce(
                CompletableFuture.completedFuture(new ArrayList<>()),
                (a, b) -> a.thenApply((r) -> {
                    r.addAll(b.join());
                    return r;
                })).join();
    }

    private static void logAndSleep(Contract c) {
        log.info("I'm in {} {}", Thread.currentThread().getName(), c.getId());
        try {
            Thread.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            // Do nothing
        }
    }
}
