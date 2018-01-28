package com.example.demo.request.slide_7_report;

import com.example.demo.domain.ReportDto;
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
            "  cl.name, " +
            "  tr.amount, " +
            "  tr.date " +
            "FROM client cl " +
            "  LEFT JOIN contract ct ON ct.client_id = cl.id " +
            "  LEFT JOIN transaction tr ON tr.contract_id = ct.id " +
            "ORDER BY cl.name";

    public List<ReportDto> getReport() {
        return jdbcTemplate.query(QUERY, new BeanPropertyRowMapper<>(ReportDto.class));
    }

}
