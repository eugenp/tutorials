package com.baeldung.beaninjectiontypes;

import com.baeldung.beaninjectiontypes.beans.DellXps;
import com.baeldung.beaninjectiontypes.beans.MacBookPro;
import com.baeldung.beaninjectiontypes.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  loader = AnnotationConfigContextLoader.class,
  classes = SpringConfig.class
)
public class SetterInjectionIntegrationTest {

    @Autowired
    private DellXps dellXps;

    @Autowired
    private MacBookPro macBookPro;

    @Test
    public void whenInjectingOptionalDependenciesThroughSetter_thenSecondaryGpuShouldBeIntelForDellXps(){
        assertNotNull(dellXps.getSecondaryGpu());
        assertEquals(dellXps.getSecondaryGpu().getName(),"Intel Onboard");
    }

    @Test
    public void whenInjectingOptionalDependenciesThroughSetter_thenSecondaryGpuShouldBeNullForMacBookPro(){
        assertNull(macBookPro.getSecondaryGpu());
    }
}
