package com.baeldung.autowired;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.differenttypesdi.autowire.Config;
import com.baeldung.differenttypesdi.shared.Control;
import com.baeldung.differenttypesdi.shared.Signal;
import com.baeldung.differenttypesdi.autowire.domain.Television;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = Config.class)
public class TVAutowireTest {

    @Autowired
    private Television television;

    @Autowired
    private Control control;

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
