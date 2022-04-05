package com.baeldung.lazy_load_no_trans;

import com.baeldung.h2db.lazy_load_no_trans.LazyLoadNoTransSpringBootApplication;
import com.baeldung.h2db.lazy_load_no_trans.service.ServiceLayer;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LazyLoadNoTransSpringBootApplication.class)
@ActiveProfiles("lazy-load-no-trans-off")
public class LazyLoadNoTransPropertyOffIntegrationTest {

    @Autowired
    private ServiceLayer serviceLayer;

    private static final long EXPECTED_DOCS_COLLECTION_SIZE = 6;

    @Test(expected = LazyInitializationException.class)
    public void whenCallNonTransactionalMethodWithPropertyOff_thenThrowException() {
        serviceLayer.countAllDocsNonTransactional();
    }

    @Test
    public void whenCallTransactionalMethodWithPropertyOff_thenTestPass() {
        SQLStatementCountValidator.reset();

        long docsCount = serviceLayer.countAllDocsTransactional();

        assertEquals(EXPECTED_DOCS_COLLECTION_SIZE, docsCount);

        SQLStatementCountValidator.assertSelectCount(2);
    }
}
