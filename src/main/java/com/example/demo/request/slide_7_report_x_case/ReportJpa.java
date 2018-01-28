package com.example.demo.request.slide_7_report_x_case;

import com.example.demo.domain.Client;
import com.example.demo.domain.ReportDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportJpa extends JpaRepository<Client, Long> {

    @Query("" +
            "select " +
            "new com.example.demo.domain.ReportDto(" +
            "  cl.name," +
            "  tr.amount," +
            "  case when (cl.name = 'Газпром') then tr.date else tr.date end" +
            ") " +
            "from Client cl " +
            "  left join cl.contracts ct " +
            "  left join ct.transactions tr " +
            "where tr.date = current_date " +
            "order by cl.name")
    List<ReportDto> getReport();
}
