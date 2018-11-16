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
    private QueueChannel multipleof3Channel;

    @Autowired
    private QueueChannel remainderIs1Channel;

    @Autowired
    private QueueChannel remainderIs2Channel;

    @Autowired
    private NumbersClassifier numbersClassifier;

    @Test
    public void whenSendMessagesToFlow_thenOutputIsCorrect() {

        numbersClassifier.flow(Arrays.asList(1, 2, 3, 4, 5, 6));
        // checking multipleof3Channel
        Message<?> outMessage = multipleof3Channel.receive(0);

        assertEquals(outMessage.getPayload(), 3);

        outMessage = multipleof3Channel.receive(0);

        assertEquals(outMessage.getPayload(), 6);
        // checking remainderIs1Channel
        outMessage = remainderIs1Channel.receive(0);

        assertEquals(outMessage.getPayload(), 1);

        outMessage = remainderIs1Channel.receive(0);
        // checking remainderIs2Channel
        assertEquals(outMessage.getPayload(), 4);
        outMessage = remainderIs2Channel.receive(0);

        assertEquals(outMessage.getPayload(), 2);

        outMessage = remainderIs2Channel.receive(0);

        assertEquals(outMessage.getPayload(), 5);

    }
}
