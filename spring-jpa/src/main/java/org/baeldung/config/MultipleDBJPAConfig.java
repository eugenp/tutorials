package org.baeldung.config;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

@Configuration
@ComponentScan({ "org.baeldung.persistence.multiple" })
@EnableTransactionManagement
public class MultipleDBJPAConfig {
    @Autowired
    private Environment env;

    public MultipleDBJPAConfig() {
        super();
    }

    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() {
        final UserTransactionImp userTransactionImp = new UserTransactionImp();
        return userTransactionImp;
    }

    @Bean(name = "atomikosTransactionManager")
    public TransactionManager atomikosTransactionManager() {
        final UserTransactionManager userTransactionManager = new UserTransactionManager();
        MyJtaPlatform.transactionManager = userTransactionManager;

        return userTransactionManager;
    }

    @Bean(name = "transactionManager")
    @DependsOn({ "userTransaction", "atomikosTransactionManager" })
    public PlatformTransactionManager transactionManager() {
        final UserTransaction userTransaction = userTransaction();

        MyJtaPlatform.transaction = userTransaction;

        final TransactionManager atomikosTransactionManager = atomikosTransactionManager();
        return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
    }
}
