package com.example.demo.request.slide_7_report_y_percent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("ALL")
@Repository
public class ReportJdbcDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String QUERY = "" +
            "SELECT " +
            "  cl.name, c.amount, c.contract_type" +
            "FROM contract c" +
            "  INNER JOIN client cl ON c.client_id= cl.id" +
            "UNION ALL " +
            "SELECT" +
            "  c.contract_type, c.amount, e.first_name|| ' ' || e.last_name" +
            "FROM contract c" +
            "  INNER JOIN employee e ON c.account_id= e.id";

    public List<SankiDto> getSankiReport() {
        return jdbcTemplate.query(QUERY, new BeanPropertyRowMapper<>(SankiDto.class));
    }

}
