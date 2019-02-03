package com.baeldung.hibernate.queryplancache;

import com.baeldung.hibernate.HibernateUtil;
import com.codahale.metrics.Timer;
import com.codahale.metrics.UniformReservoir;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.Query;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class QueryPlanCacheUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryPlanCacheUnitTest.class);

    @Parameterized.Parameters
    public static List<Integer[]> planCacheSize() {
        List<Integer[]> planCacheSizes = new ArrayList<>();

        planCacheSizes.add(new Integer[]{1});
        planCacheSizes.add(new Integer[]{2});
        planCacheSizes.add(new Integer[]{3});


        return planCacheSizes;
    }

    private Timer timer = new Timer(new UniformReservoir(9900));

    private int planCacheSize;

    public QueryPlanCacheUnitTest(int planCacheSize) {
        this.planCacheSize = planCacheSize;
    }

    @Test
    @Ignore
    public void givenQueryPlanCacheSize_thenCompileQueries() throws IOException {
        Session session = initSession();

        //warm-up
        for (int i = 0; i < 9900; i++) {
            createFindEmployeesByDepartmentNameQuery(session);
            createFindEmployeesByDesignationQuery(session);
            createFindDepartmentOfAnEmployeeQuery(session);
        }

        for (int i = 0; i < 3300; i++) {
            long startTime = System.nanoTime();
            createFindEmployeesByDepartmentNameQuery(session);
            timer.update(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);

            startTime = System.nanoTime();
            createFindEmployeesByDesignationQuery(session);
            timer.update(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);

            startTime = System.nanoTime();
            createFindDepartmentOfAnEmployeeQuery(session);
            timer.update(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        }

        LOGGER.info("Query Plan Cache Size - {}, Time(Nanoseconds) - {}", planCacheSize, timer.getSnapshot().get95thPercentile());

    }

    private Session initSession() throws IOException {
        Properties properties = HibernateUtil.getProperties();
        properties.put("hibernate.query.plan_cache_max_size", planCacheSize);
        properties.put("hibernate.query.plan_parameter_metadata_max_size", planCacheSize);
        SessionFactory sessionFactory = HibernateUtil.getSessionFactoryByProperties(properties);
        return sessionFactory.openSession();
    }

    private Query createFindEmployeesByDepartmentNameQuery(Session session) {
        return session.createQuery("SELECT e FROM DeptEmployee e " +
                "JOIN e.department WHERE e.department.name = :deptName")
                .setMaxResults(30)
                .setHint(QueryHints.HINT_FETCH_SIZE, 30);
    }

    private Query createFindEmployeesByDesignationQuery(Session session) {
        return session.createQuery("SELECT e FROM DeptEmployee e " +
                "WHERE e.title = :designation")
                .setHint(QueryHints.SPEC_HINT_TIMEOUT, 1000);
    }

    private Query createFindDepartmentOfAnEmployeeQuery(Session session) {
        return session.createQuery("SELECT e.department FROM DeptEmployee e " +
                "JOIN e.department WHERE e.employeeNumber = :empId");

    }

}
