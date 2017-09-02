package com.baeldung.explicitjava;

import com.baeldung.differenttypesdi.explicitjava.ExplicitJavaConfig;
import com.baeldung.differenttypesdi.explicitjava.domain.Television;
import com.baeldung.differenttypesdi.shared.Control;
import com.baeldung.differenttypesdi.shared.Signal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ExplicitJavaConfig.class)
public class TVExplicitJavaWireTest {

    private Television television;

    private Control control;

    @Before
    public void setBeans() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ExplicitJavaConfig.class);
        television = ctx.getBean(Television.class);
        control = ctx.getBean(Control.class);
    }

    @Test
    public void givenTelevision_ThenValidExistence() {
        assertNotNull(television);
    }

    @Test
    public void givenTelevision_WhenChangeChannel_ThenItsChanged() {
        Integer previousChannel = television.showCurrentChannel();
        control.pressButton(Signal.NEXT_CHANNEL);
        television.receiveSignal(control.emitSignal());
        Integer currentChannel = television.showCurrentChannel();
        Assert.assertNotEquals(previousChannel, currentChannel);
    }

}
