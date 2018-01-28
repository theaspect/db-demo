package com.example.demo.request.slide_7_report_y_percent;

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
            "WITH preSelect as (" +
            "  SELECT " +
            "    cl.name, " +
            "    tr.amount, " +
            "    CASE WHEN cl.name = 'Газпром' THEN tr.date ELSE tr.date END as date " +
            "  FROM client cl " +
            "    LEFT JOIN contract ct ON ct.client_id = cl.id " +
            "    LEFT JOIN transaction tr ON tr.contract_id = ct.id " +
            "  WHERE tr.date = current_date )" +
            "" +
            "SELECT" +
            "  s.name," +
            "  s.amount, " +
            "  100 * tr.amount / AVG(tr.amount)OVER(PARTITION BY s.name) " +
            "FROM preSelect s" +
            "ORDER BY s.name";

    public List<ReportDto> getReport() {
        return jdbcTemplate.query(QUERY, new BeanPropertyRowMapper<>(ReportDto.class));
    }

}
