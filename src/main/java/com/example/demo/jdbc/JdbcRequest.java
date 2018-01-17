package com.example.demo.jdbc;

import com.example.demo.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @PostConstruct
    public void init() {
        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE departments IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE departments(" +
                "id SERIAL, name VARCHAR(255))");
        List<Object[]> departments = Stream.of("Sales", "Marketing", "Development")
                .map(name -> new String[]{name})
                .collect(Collectors.toList());
        departments.forEach(name -> log.info(String.format("Inserting department record for %s", name[0])));
        jdbcTemplate.batchUpdate("INSERT INTO departments(name) VALUES (?)", departments);

        jdbcTemplate.execute("DROP TABLE employee IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE employee(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255), id_department INT, " +
                "FOREIGN KEY (id_department) REFERENCES departments(ID))");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Stream.of("John Woo 1", "Jeff Dean 2", "Josh Bloch 3", "Josh Long 1")
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting employee record for %s %s %s", name[0], name[1], name[2])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO employee(first_name, last_name, id_department) VALUES (?,?,?)", splitUpNames);
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
