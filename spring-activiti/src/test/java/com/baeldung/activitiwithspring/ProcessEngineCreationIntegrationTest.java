package com.baeldung.activitiwithspring;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProcessEngineCreationIntegrationTest {

    @Test
    public void givenXMLConfig_whenGetDefault_thenGotProcessEngine() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        assertNotNull(processEngine);
        assertEquals("root", processEngine.getProcessEngineConfiguration().getJdbcUsername());
    }

    @Test
    public void givenXMLConfig_whenCreateDefaultConfiguration_thenGotProcessEngine() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        assertNotNull(processEngine);
        assertEquals("root", processEngine.getProcessEngineConfiguration().getJdbcUsername());
    }

    @Test
    public void givenDifferentNameXMLConfig_whenGetProcessEngineConfig_thenGotResult() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("my.activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        assertNotNull(processEngine);
        assertEquals("baeldung", processEngine.getProcessEngineConfiguration().getJdbcUsername());
    }

    @Test
    public void givenDifferentBeanNameInXMLConfig_whenGetProcessEngineConfig_thenGotResult() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
          .createProcessEngineConfigurationFromResource("my.activiti.cfg.xml", "myProcessEngineConfiguration");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        assertNotNull(processEngine);
        assertEquals("baeldung", processEngine.getProcessEngineConfiguration().getJdbcUsername());
    }

    @Test
    public void givenNoXMLConfig_whenCreateInMemProcessEngineConfig_thenCreated() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine processEngine = processEngineConfiguration
          .setJdbcUrl("jdbc:h2:mem:my-own-in-mem-db;DB_CLOSE_DELAY=1000")
          .buildProcessEngine();
        assertNotNull(processEngine);
        assertEquals("sa", processEngine.getProcessEngineConfiguration().getJdbcUsername());
    }

    @Test
    public void givenNoXMLConfig_whenCreateProcessEngineConfig_thenCreated() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        ProcessEngine processEngine = processEngineConfiguration
          .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
          .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
          .buildProcessEngine();
        assertNotNull(processEngine);
        assertEquals("sa", processEngine.getProcessEngineConfiguration().getJdbcUsername());
    }
}
