package com.baeldung.dddhexagonalspringboot.adapters.secondary.workorder.rabbitmq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;

@RunWith(MockitoJUnitRunner.class)
public class WorkOrderRabbitAdapterUnitTest {

    @Spy
    @InjectMocks
    private WorkOrderRabbitMQAdapter tested;

    @Mock
    private AmqpTemplate amqpTemplate;

    @Mock
    private Queue queue;

    @Test
    public void whenCreate_thenMessageIsSentToQueue() {
        when(queue.getName()).thenReturn("mock-name");
        doNothing().when(amqpTemplate)
            .convertAndSend(anyString(), any(WorkOrderJSON.class));
        WorkOrder workOrder = tested.create(createWorkOrderForTesting());
        assertNotNull(workOrder);
        ArgumentCaptor<String> queueNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<WorkOrderJSON> woJSONCaptor = ArgumentCaptor.forClass(WorkOrderJSON.class);
        verify(amqpTemplate, times(1)).convertAndSend(queueNameCaptor.capture(), woJSONCaptor.capture());
        assertEquals("mock-name", queueNameCaptor.getValue());
        assertEquals(workOrder.getId().longValue(), woJSONCaptor.getValue().getId().longValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenRead_thenUnsupportedOperationExceptionIsThrown() {
        tested.read(12345l);
    }

    private WorkOrder createWorkOrderForTesting() {
        WorkOrder wo = new WorkOrder();
        wo.setDate(LocalDateTime.now());
        return wo;
    }

}
