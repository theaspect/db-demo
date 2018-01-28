package com.example.demo.request.slide_5_lazy;

import com.example.demo.request.slide_4_projection.EmloyeeJpaDao4;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SuppressWarnings("ALL")
@SpringBootTest
@WebAppConfiguration
public class EmployeeJpaDao4Test {

    @Autowired
    EmloyeeJpaDao4 dao;

    @Test
    public void testCriteriaBuilder() throws Exception {
        assertEquals(dao.getCriteriaShortEmployeeByDepartmentId(1L).size(), 3);
    }

    @Test
    public void testHql() throws Exception {
        assertEquals(dao.getShortEmployeeByDepartmentId(1L).size(), 3);
    }
}
