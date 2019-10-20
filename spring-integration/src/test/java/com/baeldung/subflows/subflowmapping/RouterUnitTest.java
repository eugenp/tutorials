package com.baeldung.subflows.subflowmapping;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.subflows.subflowmapping.RouterExample.NumbersClassifier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RouterExample.class })
public class RouterUnitTest {
    @Autowired
    private QueueChannel multipleofThreeChannel;

    @Autowired
    private QueueChannel remainderIsOneChannel;

    @Autowired
    private QueueChannel remainderIsTwoChannel;

    @Autowired
    private NumbersClassifier numbersClassifier;

    @Test
    public void whenSendMessagesToFlow_thenNumbersAreClassified() {

        numbersClassifier.classify(Arrays.asList(1, 2, 3, 4, 5, 6));

        Message<?> outMessage = multipleofThreeChannel.receive(0);

        assertEquals(outMessage.getPayload(), 3);

        outMessage = multipleofThreeChannel.receive(0);

        assertEquals(outMessage.getPayload(), 6);

        outMessage = remainderIsOneChannel.receive(0);

        assertEquals(outMessage.getPayload(), 1);
        outMessage = remainderIsOneChannel.receive(0);

        assertEquals(outMessage.getPayload(), 4);

        outMessage = remainderIsTwoChannel.receive(0);

        assertEquals(outMessage.getPayload(), 2);

        outMessage = remainderIsTwoChannel.receive(0);

        assertEquals(outMessage.getPayload(), 5);

    }

}
