package com.baeldung.component.inscope;

import com.baeldung.component.outsidescope.OutsideScopeBeanExample;
import com.baeldung.component.outsidescope.OutsideScopeExample;
import com.baeldung.component.scannedscope.ScannedScopeExample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ComponentUnitTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void givenInScopeComponents_whenSearchingInApplicationContext_thenFindThem() {
        assertNotNull(applicationContext.getBean(ControllerExample.class));
        assertNotNull(applicationContext.getBean(ServiceExample.class));
        assertNotNull(applicationContext.getBean(RepositoryExample.class));
        assertNotNull(applicationContext.getBean(ComponentExample.class));
        assertNotNull(applicationContext.getBean(CustomComponentExample.class));
    }

    @Test
    public void givenOutsideScopeComponent_whenSearchingInApplicationContext_thenFail() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(OutsideScopeExample.class));
    }

    @Test
    public void givenScannedScopeComponent_whenSearchingInApplicationContext_thenFindIt() {
        assertNotNull(applicationContext.getBean(ScannedScopeExample.class));
    }

    @Test
    public void givenBeanComponents_whenSearchingInApplicationContext_thenFindThem() {
        assertNotNull(applicationContext.getBean(BeanExample.class));
        assertNotNull(applicationContext.getBean(OutsideScopeBeanExample.class));
    }

    @Test
    public void givenAmbiguousBeanSetToB_whenSearchingInApplicationContext_thenFindImplB() {
        AmbiguousBean ambiguousBean = applicationContext.getBean(AmbiguousBean.class);
        assertNotNull(ambiguousBean);
        assertTrue(ambiguousBean instanceof BeanImplB);
    }
}