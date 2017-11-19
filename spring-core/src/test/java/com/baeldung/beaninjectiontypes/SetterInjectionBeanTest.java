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
public class SetterInjectionBeanTest {

    @Autowired
    private SetterInjectionBean setterFamilyBean;

    @Test
    public void testSetterInjectionBean() {
        assertNotNull(setterFamilyBean);
    }

    @Test
    public void testNoOfChildren() {
        assertTrue(setterFamilyBean.noOfChildren() == 0);
        setterFamilyBean.addChildToFamily("Bimbo");
        setterFamilyBean.addChildToFamily("Bayo");
        assertTrue(setterFamilyBean.noOfChildren() == 2);
    }

    @Test
    public void testAddChildToFamily() {
        assertFalse(setterFamilyBean.isChildAlive("Damola"));
        setterFamilyBean.addChildToFamily("Damola");
        assertTrue(setterFamilyBean.isChildAlive("Damola"));
        setterFamilyBean.killChild("Damola");
    }

}
