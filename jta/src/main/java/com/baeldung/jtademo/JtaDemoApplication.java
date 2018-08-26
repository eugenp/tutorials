package com.baeldung.jtademo;

import com.baeldung.jtademo.services.TellerService;
import org.hsqldb.jdbc.JDBCDataSourceFactory;
import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jta.bitronix.BitronixXADataSourceWrapper;
import org.springframework.boot.jta.bitronix.PoolingDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@EnableAutoConfiguration
@EnableTransactionManagement
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
        BitronixXADataSourceWrapper wrapper = new BitronixXADataSourceWrapper();
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
