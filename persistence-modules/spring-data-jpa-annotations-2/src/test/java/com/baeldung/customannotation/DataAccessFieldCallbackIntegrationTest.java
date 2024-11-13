package com.baeldung.customannotation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Type;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CustomAnnotationConfiguration.class })
public class DataAccessFieldCallbackIntegrationTest {

    @Autowired
    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Autowired
    private BeanWithGenericDAO beanWithGenericDAO;

    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Test
    public void whenObjectCreated_thenObjectCreationIsSuccessful() {
        final DataAccessFieldCallback dataAccessFieldCallback = new DataAccessFieldCallback(configurableListableBeanFactory, beanWithGenericDAO);
        assertThat(dataAccessFieldCallback, is(notNullValue()));
    }

    @Test
    public void whenMethodGenericTypeIsValidCalled_thenReturnCorrectValue() throws NoSuchFieldException, SecurityException {
        final DataAccessFieldCallback callback = new DataAccessFieldCallback(configurableListableBeanFactory, beanWithGenericDAO);
        final Type fieldType = BeanWithGenericDAO.class.getDeclaredField("personGenericDAO").getGenericType();
        final boolean result = callback.genericTypeIsValid(Person.class, fieldType);
        assertThat(result, is(true));
    }

    @Test
    public void whenMethodGetBeanInstanceCalled_thenReturnCorrectInstance() {
        final DataAccessFieldCallback callback = new DataAccessFieldCallback(configurableListableBeanFactory, beanWithGenericDAO);
        final Object result = callback.getBeanInstance("personGenericDAO", GenericDAO.class, Person.class);
        assertThat((result instanceof GenericDAO), is(true));
    }
}
