package com.baeldung.hexagonalarchitecture.config;

import com.baeldung.hexagonalarchitecture.domain.user.User;
import com.baeldung.hexagonalarchitecture.domain.user.UserRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Class comments go here...
 *
 * @author José Carlos Mazella Junior
 * @version 1.0 10/12/2018
 */
@Configuration
@EntityScan(basePackageClasses = {User.class})
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
public class DatabaseConfiguration extends JpaBaseConfiguration {

    public DatabaseConfiguration(final DataSource dataSource, final JpaProperties properties,
                                 final ObjectProvider<JtaTransactionManager> jtaTransactionManager,
                                 final ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("eclipselink.weaving", "static");
        return properties;
    }

}
