package com.baeldung.lazy_load_no_trans;

import com.baeldung.h2db.lazy_load_no_trans.LazyLoadNoTransSpringBootApplication;
import com.baeldung.h2db.lazy_load_no_trans.service.ServiceLayer;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import net.ttddyy.dsproxy.listener.logging.DefaultQueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LazyLoadNoTransSpringBootApplication.class)
@ActiveProfiles("lazy-load-no-trans-off")
public class LazyLoadNoTransPropertyOffIntegrationTest {

    @Autowired
    private ServiceLayer serviceLayer;

    private static final long EXPECTED_DOCS_COLLECTION_SIZE = 6;

    @Bean
    public DataSource dataSource() {
        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
        loggingListener.setQueryLogEntryCreator(new DefaultQueryLogEntryCreator());
        return ProxyDataSourceBuilder.create()
            .countQuery()
            .listener(loggingListener)
            .build();
    }

    @Test(expected = LazyInitializationException.class)
    public void should_call_non_transactional_method() {
        serviceLayer.countAllDocsNonTransactional();
    }

    @Test
    public void should_call_transactional_method() {
        SQLStatementCountValidator.reset();
        long docsCount = serviceLayer.countAllDocsTransactional();
        Assert.assertEquals(EXPECTED_DOCS_COLLECTION_SIZE, docsCount);
        SQLStatementCountValidator.assertSelectCount(2);
    }
}
