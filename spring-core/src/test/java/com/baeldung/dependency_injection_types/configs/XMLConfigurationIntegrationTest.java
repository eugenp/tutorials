package com.baeldung.dependency_injection_types.configs;

import com.baeldung.dependency_injection_types.repositories.CategoryRepository;
import com.baeldung.dependency_injection_types.runners.CategoryImporter;
import com.baeldung.dependency_injection_types.runners.CategoryListRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = XMLConfiguration.class)
public class XMLConfigurationIntegrationTest {

    @Autowired private ApplicationContext applicationContext;

    @Test
    public void whenAutowiredOnConstructor_ThenCategoryRepositoryDependencyResolved() {
        assertNotNull(applicationContext.getBean(CategoryRepository.class));
    }

    @Test
    public void whenAutowiredOnConstructor_ThenCategoryImporterDependencyResolved() {
        assertNotNull(applicationContext.getBean(CategoryImporter.class));
    }

    @Test
    public void whenAutowiredOnSetter_ThenCategoryListRunnerDependencyResolved() {
        assertNotNull(applicationContext.getBean(CategoryListRunner.class));
    }
}
