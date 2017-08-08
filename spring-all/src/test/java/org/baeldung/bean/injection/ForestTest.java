package org.baeldung.bean.injection;

import org.baeldung.bean.config.ForestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ForestTest {
    @Test
    public void checkAnimalInjection() {
    	AnnotationConfigApplicationContext cont = new AnnotationConfigApplicationContext();
    	cont.register(ForestConfig.class);
    	cont.refresh();

    	Forest forest = cont.getBean(Forest.class);

        assert forest.getAnimal() != null;
    }

}
