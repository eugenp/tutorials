package org.baeldung.startup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringStartupConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SpringStartupTest {

    @Autowired
    private ApplicationContext ctx;

    @Test(expected = BeanCreationException.class)
    public void whenInstantiating_shouldThrowNPE() throws Exception {
        ctx.getBean(LogicInConstructorExampleBean.class);
    }


}