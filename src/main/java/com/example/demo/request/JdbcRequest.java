package com.example.demo.request;

import com.example.demo.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Based on https://spring.io/guides/gs/relational-data-access/
 */
@Component
public class JdbcRequest {
    private static final Logger log = LoggerFactory.getLogger(JdbcRequest.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcRequest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> getJosh(){
        log.info("Querying for employee records where first_name = 'Josh':");
        return jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM employee WHERE first_name = ?",
                new Object[] { "Josh" },
                (rs, rowNum) -> new Employee(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), null)
        );
    }
}
