package com.baeldung.profiles;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class SpringProfilesWithXMLIntegrationTest {

    private ClassPathXmlApplicationContext classPathXmlApplicationContext;

    @Test
    public void testSpringProfilesForDevEnvironment() {
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:springProfiles-config.xml");
        final ConfigurableEnvironment configurableEnvironment = classPathXmlApplicationContext.getEnvironment();
        configurableEnvironment.setActiveProfiles("dev");
        classPathXmlApplicationContext.refresh();
        final DatasourceConfig datasourceConfig = classPathXmlApplicationContext.getBean("devDatasourceConfig", DatasourceConfig.class);

        Assert.assertTrue(datasourceConfig instanceof DevDatasourceConfig);
    }

    @Test
    public void testSpringProfilesForProdEnvironment() {
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:springProfiles-config.xml");
        final ConfigurableEnvironment configurableEnvironment = classPathXmlApplicationContext.getEnvironment();
        configurableEnvironment.setActiveProfiles("production");
        classPathXmlApplicationContext.refresh();
        final DatasourceConfig datasourceConfig = classPathXmlApplicationContext.getBean("productionDatasourceConfig", DatasourceConfig.class);

        Assert.assertTrue(datasourceConfig instanceof ProductionDatasourceConfig);
    }
}
