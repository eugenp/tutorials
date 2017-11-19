package com.baeldung.beaninjectiontypes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.beaninjectiontypes.models.Family;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BeanConfig.class })
public class ConstructorInjectionBeanTest {

    @Autowired
    private ConstructorInjectionBean constructorFamilyBean;

    @Test
    public void testConstructorInjectionBean() {
        assertNotNull(constructorFamilyBean);
    }

    @Test
    public void testNoOfChildren() {
        assertTrue(constructorFamilyBean.noOfChildren() == 0);
        constructorFamilyBean.addChildToFamily("Bimbo");
        constructorFamilyBean.addChildToFamily("Bayo");
        assertTrue(constructorFamilyBean.noOfChildren() == 2);
    }

    @Test
    public void testAddChildToFamily() {
        assertFalse(constructorFamilyBean.isChildAlive("Damola"));
        constructorFamilyBean.addChildToFamily("Damola");
        assertTrue(constructorFamilyBean.isChildAlive("Damola"));
        constructorFamilyBean.killChild("Damola");
    }

}
