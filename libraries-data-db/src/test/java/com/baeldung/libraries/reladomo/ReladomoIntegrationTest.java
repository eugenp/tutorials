package com.baeldung.libraries.reladomo;

import com.gs.fw.common.mithra.test.ConnectionManagerForTests;
import com.gs.fw.common.mithra.test.MithraTestResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReladomoIntegrationTest {
    private MithraTestResource mithraTestResource;

    @Before
    public void setUp() throws Exception {
        this.mithraTestResource = new MithraTestResource("reladomo/ReladomoTestConfig.xml");

        final ConnectionManagerForTests connectionManager = ConnectionManagerForTests.getInstanceForDbName("testDb");
        this.mithraTestResource.createSingleDatabase(connectionManager);

        mithraTestResource.addTestDataToDatabase("reladomo/test-data.txt", connectionManager);

        this.mithraTestResource.setUp();
    }

    @Test
    public void whenGetTestData_thenOk() {
        Employee employee = EmployeeFinder.findByPrimaryKey(1);
        assertEquals(employee.getName(), "Paul");
    }

    @After
    public void tearDown() throws Exception {
        this.mithraTestResource.tearDown();
    }

}
