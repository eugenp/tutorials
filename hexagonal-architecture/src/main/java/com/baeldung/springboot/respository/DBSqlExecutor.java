package com.baeldung.springboot.respository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.baeldung.springboot.entity.Person;
import org.h2.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ClassUtils;


@Repository
public class DBSqlExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBSqlExecutor.class);

    public List<Person> executeSimpleSql(String simpleSql) throws IllegalAccessException, InvocationTargetException, InstantiationException {

        LOGGER.info("DBSqlExecutor: executeSimpleSql :: simpleSql -> {} ", simpleSql);

        return getJdbcTemplate().query(simpleSql, new BeanPropertyRowMapper<Person>(Person.class));
    }

    public List<Person> executeSqlWithParam(Map<String, String> sql) throws IllegalAccessException, InvocationTargetException, InstantiationException {

        LOGGER.info("DBSqlExecutor: executeSqlWithParam :: sql with Param -> {}, {} ", sql.get("sqlQuery"), sql.get("paramValue"));

        return getJdbcTemplate().query(sql.get("sqlQuery"), new Object[]{sql.get("paramValue")}, new BeanPropertyRowMapper<Person>(Person.class));

    }


    @Bean
    private JdbcTemplate getJdbcTemplate() throws IllegalAccessException, InvocationTargetException, InstantiationException {

        final String driverClassName = "org.h2.Driver";
        final String jdbcUrl = "jdbc:h2:mem:personsdb;DB_CLOSE_ON_EXIT=FALSE";
        final String username = "run";
        final String password = "";
        // Build dataSource & JDBC template manually
        final Class<?> driverClass = ClassUtils.resolveClassName(driverClassName, this.getClass().getClassLoader());
        final Driver driver = (Driver) ClassUtils.getConstructorIfAvailable(driverClass).newInstance();
        final DataSource dataSource = new SimpleDriverDataSource(driver, jdbcUrl, username, password);
        return new JdbcTemplate(dataSource);
    }

}
