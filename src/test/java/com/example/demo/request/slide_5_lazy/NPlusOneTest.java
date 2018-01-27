package com.example.demo.request.slide_5_lazy;

import com.example.demo.request.slide_6_nplus1.EmloyeeJpaDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

@RunWith(SpringRunner.class)
@SuppressWarnings("ALL")
@SpringBootTest
@WebAppConfiguration
public class NPlusOneTest {

    @Autowired
    EmloyeeJpaDao emloyeeJpaDao;

    @Test
    public void testEmployeeConverter() throws Exception {
        // *** In thread main executed 49 queries 64 ms
        final Map<Long, Long> contractsByEmployee = emloyeeJpaDao.getContractsByEmployee();

        Assert.assertTrue(contractsByEmployee.size() == 33);
    }

    @Test
    public void testEmployeeConverterEager() throws Exception {
        // *** In thread main executed 16 queries 24 ms
        final Map<Long, Long> contractsByEmployee = emloyeeJpaDao.getContractsByEmployeeEager();

        Assert.assertTrue(contractsByEmployee.size() == 33);
    }

    /**
     * HibernateException: cannot simultaneously fetch multiple bags
     * Here is a link to an Hibernate official "issue" about this: https://hibernate.atlassian.net/browse/HHH-1718.
     * As you can see, it has been open in 2006 and is still open.
     *
     * We can use Set instead of List to query nested left joins
     */
    @Test
    public void testEmployeeConverterNotSoEagerest() throws Exception {
        // *** In thread main executed 16 queries 408 ms
        final Map<Long, Long> contractsByEmployee = emloyeeJpaDao.getContractsByEmployeeNotSoEagerest();

        Assert.assertTrue(contractsByEmployee.size() == 33);
    }

    @Test
    public void testEmployeeConverterEagerest() throws Exception {
        // *** In thread main executed 2 queries 25 ms
        final Map<Long, Long> contractsByEmployee = emloyeeJpaDao.getContractsByEmployeeEagerest();

        Assert.assertTrue(contractsByEmployee.size() == 33);
    }
}
