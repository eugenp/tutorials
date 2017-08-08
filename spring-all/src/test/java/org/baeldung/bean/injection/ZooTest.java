package org.baeldung.bean.injection;

import org.baeldung.bean.config.ZooConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ForestTest {
    @Test
    public void checkAnimalInjection() {
    	AnnotationConfigApplicationContext cont = new AnnotationConfigApplicationContext();
    	cont.register(ZooConfig.class);
    	cont.refresh();

    	Zoo zoo = cont.getBean(Zoo.class);

        assert zoo.getAnimalOfZoo() != null;
    }

}
