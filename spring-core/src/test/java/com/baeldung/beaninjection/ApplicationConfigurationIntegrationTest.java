package com.baeldung.beaninjection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = ApplicationConfiguration.class)
public class ApplicationConfigurationIntegrationTest {

    @Autowired
    private Computer computer;

    @Test
    public void givenAutowiredField_whenItsDependencyAutowiredOnConstructor_thenDependencyResolved() {
        Processor processor = computer.getProcessor();

        assertNotNull(processor);
        assertThat(processor.getName(), equalTo("Intel Core i7 4790K"));
    }

    @Test
    public void givenAutowiredField_whenItsDependencyAutowiredOnField_thenDependencyResolved() {
        Motherboard motherboard = computer.getMotherboard();

        assertNotNull(motherboard);
        assertThat(motherboard.getName(), equalTo("ASUS Z170-K"));
    }

    @Test
    public void givenAutowiredField_whenItsDependencyAutowiredOnSetter_thenDependencyResolved() {
        VideoCard videoCard = computer.getVideoCard();

        assertNotNull(videoCard);
        assertThat(videoCard.getName(), equalTo("MSI GeForce GTX 1050"));
    }

}
