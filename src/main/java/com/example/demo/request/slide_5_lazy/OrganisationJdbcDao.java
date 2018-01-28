package com.example.demo.request.slide_5_lazy;

import com.example.demo.domain.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("ALL")
@Repository
public class OrganisationJdbcDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String QUERY = "" +
            "SELECT \n" +
            "  * \n" +
            "FROM organisation o \n" +
            "  LEFT JOIN department d ON o.id = d.organisation_id \n" +
            "  LEFT JOIN employee e ON d.id = e.department_id \n" +
            "ORDER BY o.id, d.id DESC, e.id DESC";

    public List<Organisation> findAllAndFetch() {
        return jdbcTemplate.query(QUERY, new BeanPropertyRowMapper<>(Organisation.class));
    }

}
