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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  loader = AnnotationConfigContextLoader.class,
  classes = SpringConfig.class
)
public class ConstructorInjectionIntegrationTest {

    @Autowired
    private DellXps dellXps;

    @Autowired
    private MacBookPro macBookPro;

    @Test
    public void whenInjectionRequiredDependenciesThroughConstructor_thenProcessorShouldBeIntelAndPrimaryGpuShouldBeNvdiaForDellXps(){
        assertNotNull(dellXps);

        assertNotNull(dellXps.getPrimaryGpu());
        assertNotNull(dellXps.getProcessor());

        assertEquals(dellXps.getPrimaryGpu().getName(),"Nvdia GTX 1080");
        assertEquals(dellXps.getProcessor().getName(),"Intel Core i7");
    }

    @Test
    public void whenInjectionRequiredDependenciesThroughConstructor_thenProcessorShouldBeIntelAndPrimaryGpuShouldBeIntelOnboardForMacBookPro(){
        assertNotNull(macBookPro);

        assertNotNull(macBookPro.getPrimaryGpu());
        assertNotNull(macBookPro.getProcessor());

        assertEquals(macBookPro.getPrimaryGpu().getName(),"Intel Onboard");
        assertEquals(macBookPro.getProcessor().getName(),"Intel Core i7");
    }

}
