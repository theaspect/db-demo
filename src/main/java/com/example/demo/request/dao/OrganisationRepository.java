package com.example.demo.request.dao;

import com.example.demo.domain.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    @Query("" +
            "select distinct o " +
            "from Organisation o " +
            "  left join fetch o.departments d " +
            "  left join fetch d.employees e " +
            "order by o.id, d.id, e.id ")
    Collection<Organisation> findAllAndFetch();

}
