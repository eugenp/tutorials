package com.baeldung.ex.nontransientdataaccessexception;

import javax.sql.DataSource;

import com.baeldung.ex.nontransientexception.cause.Cause4NonTransientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause4NonTransientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class DataSourceLookupExceptionManualTest {

    @Test(expected = DataSourceLookupFailureException.class)
    public void whenLookupNonExistentDataSource_thenDataSourceLookupFailureException() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        final DataSource dataSource = dsLookup.getDataSource("java:comp/env/jdbc/example_db");
    }
}
