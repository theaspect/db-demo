package com.example.demo.request.slide_5_nplus1;

import com.example.demo.domain.Client;
import com.example.demo.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmployeeJpaService {
    final EmloyeeJpaDao6 dao;

    @Autowired
    public EmployeeJpaService(EmloyeeJpaDao6 dao) {
        this.dao = dao;
    }


    public Map<Long, Long> getContractsByEmployee() {
        Map<Long, Long> result = new HashMap<>();
        for (Employee e : dao.getAll()) {
            Long cnt = 0L;
            for (Client c : e.getClients()) {
                cnt += c.getContracts().size();
            }
            result.put(e.getId(), cnt);
        }
        return result;
    }
}
