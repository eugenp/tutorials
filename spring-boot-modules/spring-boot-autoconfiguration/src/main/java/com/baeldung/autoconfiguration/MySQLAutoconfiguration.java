package com.baeldung.autoconfiguration;

import java.util.Arrays;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionMessage.Style;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.ClassUtils;

@Configuration
@ConditionalOnClass(DataSource.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@PropertySource("classpath:mysql.properties")
public class MySQLAutoconfiguration {

    @Autowired
    private Environment env;

    @Bean
    @ConditionalOnProperty(name = "usemysql", havingValue = "local")
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/myDb?createDatabaseIfNotExist=true&&serverTimezone=UTC");
        dataSource.setUsername("mysqluser");
        dataSource.setPassword("mysqlpass");

        return dataSource;
    }

    @Bean(name = "dataSource")
    @ConditionalOnProperty(name = "usemysql", havingValue = "custom")
    @ConditionalOnMissingBean
    public DataSource dataSource2() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(env.getProperty("mysql.url"));
        dataSource.setUsername(env.getProperty("mysql.user") != null ? env.getProperty("mysql.user") : "");
        dataSource.setPassword(env.getProperty("mysql.pass") != null ? env.getProperty("mysql.pass") : "");

        return dataSource;
    }

    @Bean
    @ConditionalOnBean(name = "dataSource")
    @ConditionalOnMissingBean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.baeldung.autoconfiguration.example");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        if (additionalProperties() != null) {
            em.setJpaProperties(additionalProperties());
        }
        return em;
    }

    @Bean
    @ConditionalOnMissingBean(type = "JpaTransactionManager")
    JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @ConditionalOnResource(resources = "classpath:mysql.properties")
    @Conditional(HibernateCondition.class)
    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("mysql-hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("mysql-hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("mysql-hibernate.show_sql") != null ? env.getProperty("mysql-hibernate.show_sql") : "false");

        return hibernateProperties;
    }

    static class HibernateCondition extends SpringBootCondition {

        private static final String[] CLASS_NAMES = { "org.hibernate.ejb.HibernateEntityManager", "org.hibernate.jpa.HibernateEntityManager" };

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("Hibernate");

            return Arrays.stream(CLASS_NAMES).filter(className -> ClassUtils.isPresent(className, context.getClassLoader())).map(className -> ConditionOutcome.match(message.found("class").items(Style.NORMAL, className))).findAny()
                    .orElseGet(() -> ConditionOutcome.noMatch(message.didNotFind("class", "classes").items(Style.NORMAL, Arrays.asList(CLASS_NAMES))));
        }

    }
}
