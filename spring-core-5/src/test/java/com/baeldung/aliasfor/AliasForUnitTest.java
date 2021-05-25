package com.baeldung.aliasfor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyMappingController.class)
public class AliasForUnitTest {

    @Autowired
    private ConfigurableApplicationContext context;

    Class controllerClass;

    @Before
    public void setControllerBean() {
        MyMappingController controllerBean = context.getBean(MyMappingController.class);
        controllerClass = controllerBean.getClass();
    }

    @Test
    public void givenComposedAnnotation_whenExplicitAlias_thenMetaAnnotationAttributeOverridden() {

        for (Method method : controllerClass.getMethods()) {
            if (method.isAnnotationPresent(MyMapping.class)) {
                MyMapping annotation = AnnotationUtils.findAnnotation(method, MyMapping.class);
                RequestMapping metaAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);

                assertEquals(RequestMethod.PATCH, annotation.action()[0]);

                assertEquals(0, metaAnnotation.method().length);
            }
        }
    }

    @Test
    public void givenComposedAnnotation_whenImplictAlias_thenAttributesEqual() {
        for (Method method : controllerClass.getMethods()) {
            if (method.isAnnotationPresent(MyMapping.class)) {
                MyMapping annotationOnBean = AnnotationUtils.findAnnotation(method, MyMapping.class);

                assertTrue(annotationOnBean.mapping()[0].equals(annotationOnBean.route()[0]));
                assertTrue(annotationOnBean.value()[0].equals(annotationOnBean.route()[0]));
            }
        }
    }

}
