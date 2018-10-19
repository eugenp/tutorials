package org.baeldung.bean.injection;

import org.baeldung.bean.config.ConstructorBasedShipConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConstructorBasedBeanInjectionWithJavaConfigIntegrationTest {
    private static final String HELM_NAME = "HelmBrand";

    @Test
    public void givenJavaConfigFile_whenUsingConstructorBasedBeanInjection_thenCorrectHelmName() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ConstructorBasedShipConfig.class);
        ctx.refresh();

        Ship ship = ctx.getBean(Ship.class);

        Assert.assertEquals(HELM_NAME, ship.getHelm().getBrandOfHelm());
    }
}
