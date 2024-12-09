package com.baeldung.hibernate.queryplancache;

import com.baeldung.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.Query;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class QueryPlanCacheBenchmark {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryPlanCacheBenchmark.class);

    @State(Scope.Thread)
    public static class QueryPlanCacheBenchMarkState {
        @Param({"1", "2", "3"})
        public int planCacheSize;

        public Session session;

        @Setup
        public void stateSetup() throws IOException {
            LOGGER.info("State - Setup");
            session = initSession(planCacheSize);
            LOGGER.info("State - Setup Complete");
        }

        private Session initSession(int planCacheSize) throws IOException {
            Properties properties = HibernateUtil.getProperties();
            properties.put("hibernate.query.plan_cache_max_size", planCacheSize);
            properties.put("hibernate.query.plan_parameter_metadata_max_size", planCacheSize);
            SessionFactory sessionFactory = HibernateUtil.getSessionFactoryByProperties(properties);
            return sessionFactory.openSession();
        }

        @TearDown
        public void tearDownState() {
            LOGGER.info("State - Teardown");
            SessionFactory sessionFactory = session.getSessionFactory();
            session.close();
            sessionFactory.close();
            LOGGER.info("State - Teardown complete");
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 5)
    public void givenQueryPlanCacheSize_thenCompileQueries(QueryPlanCacheBenchMarkState state, Blackhole blackhole) {

        Query query1 = findEmployeesByDepartmentNameQuery(state.session);
        Query query2 = findEmployeesByDesignationQuery(state.session);
        Query query3 = findDepartmentOfAnEmployeeQuery(state.session);

        blackhole.consume(query1);
        blackhole.consume(query2);
        blackhole.consume(query3);

    }

    private Query findEmployeesByDepartmentNameQuery(Session session) {
        return session.createQuery("SELECT e FROM DeptEmployee e " +
                "JOIN e.department WHERE e.department.name = :deptName")
                .setMaxResults(30)
                .setHint(QueryHints.HINT_FETCH_SIZE, 30);
    }

    private Query findEmployeesByDesignationQuery(Session session) {
        return session.createQuery("SELECT e FROM DeptEmployee e " +
                "WHERE e.title = :designation")
                .setHint(QueryHints.SPEC_HINT_TIMEOUT, 1000);
    }

    private Query findDepartmentOfAnEmployeeQuery(Session session) {
        return session.createQuery("SELECT e.department FROM DeptEmployee e " +
                "JOIN e.department WHERE e.employeeNumber = :empId");

    }

    public static void main(String... args) throws IOException, RunnerException {
        //main-class to run the benchmark
        org.openjdk.jmh.Main.main(args);
    }
}
