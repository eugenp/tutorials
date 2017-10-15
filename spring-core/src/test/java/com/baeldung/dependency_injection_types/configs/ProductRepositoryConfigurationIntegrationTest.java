package com.baeldung.dependency_injection_types.configs;

import com.baeldung.dependency_injection_types.repositories.ProductRepository;
import com.baeldung.dependency_injection_types.runners.ProductImporter;
import com.baeldung.dependency_injection_types.runners.ProductListRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ProductRepositoryConfiguration.class)
public class ProductRepositoryConfigurationIntegrationTest {

    @Autowired private ApplicationContext applicationContext;

    @Test
    public void whenAutowiredOnConstructor_ThenProductRepositoryDependencyResolved() {
        assertNotNull(applicationContext.getBean(ProductRepository.class));
    }

    @Test
    public void whenAutowiredOnConstructor_ThenProductImporterDependencyResolved() {
        assertNotNull(applicationContext.getBean(ProductImporter.class));
    }

    @Test
    public void whenAutowiredOnSetter_ThenProductListRunnerDependencyResolved() {
        assertNotNull(applicationContext.getBean(ProductListRunner.class));
    }
}
