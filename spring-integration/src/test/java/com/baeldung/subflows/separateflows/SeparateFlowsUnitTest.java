package com.baeldung.subflows.separateflows;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import com.baeldung.subflows.separateflows.SeparateFlowsExample.NumbersClassifier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SeparateFlowsExample.class })
public class SeparateFlowsUnitTest {
    @Autowired
    private QueueChannel multipleof3Channel;

    @Autowired
    private QueueChannel remainderIs1Channel;

    @Autowired
    private QueueChannel remainderIs2Channel;

    @Autowired
    private NumbersClassifier numbersClassifier;

    @Test
    public void whenSendMessagesToMultipleof3Flow_thenOutputMultiplesof3() {

        numbersClassifier.multipleof3(Arrays.asList(1, 2, 3, 4, 5, 6));

        Message<?> outMessage = multipleof3Channel.receive(0);

        assertEquals(outMessage.getPayload(), 3);

        outMessage = multipleof3Channel.receive(0);

        assertEquals(outMessage.getPayload(), 6);

    }

    @Test
    public void whenSendMessagesToremainderIs1Flow_thenOutputRemainderIs1() {

        numbersClassifier.remainderIs1(Arrays.asList(1, 2, 3, 4, 5, 6));

        Message<?> outMessage = remainderIs1Channel.receive(0);

        assertEquals(outMessage.getPayload(), 1);

        outMessage = remainderIs1Channel.receive(0);

        assertEquals(outMessage.getPayload(), 4);

    }

    @Test
    public void whenSendMessagesToremainderIs1Flow_thenOutputRemainderIs2() {

        numbersClassifier.remainderIs2(Arrays.asList(1, 2, 3, 4, 5, 6));

        Message<?> outMessage = remainderIs2Channel.receive(0);

        assertEquals(outMessage.getPayload(), 2);

        outMessage = remainderIs2Channel.receive(0);

        assertEquals(outMessage.getPayload(), 5);
        
        outMessage = remainderIs2Channel.receive(0);


    }

}
