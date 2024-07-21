package com.baeldung.jtademo;

import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import com.atomikos.spring.AtomikosXADataSourceWrapper;

@EnableAutoConfiguration
@EnableTransactionManagement
@Configuration
@ComponentScan("com.baeldung.jtademo")
@EnableJpaRepositories
public class JtaDemoApplication {

    @Bean("dataSourceAccount")
    public DataSource dataSource() throws Exception {
        return createHsqlXADatasource("jdbc:hsqldb:mem:accountDb");
    }

    @Bean("dataSourceAudit")
    public DataSource dataSourceAudit() throws Exception {
        return createHsqlXADatasource("jdbc:hsqldb:mem:auditDb");
    }

    private DataSource createHsqlXADatasource(String connectionUrl) throws Exception {
        JDBCXADataSource dataSource = new JDBCXADataSource();
        dataSource.setUrl(connectionUrl);
        dataSource.setUser("sa");
        AtomikosXADataSourceWrapper wrapper = new AtomikosXADataSourceWrapper();
        return wrapper.wrapDataSource(dataSource);
    }

    @Bean("jdbcTemplateAccount")
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSourceAccount") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean("jdbcTemplateAudit")
    public JdbcTemplate jdbcTemplateAudit(@Qualifier("dataSourceAudit") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
