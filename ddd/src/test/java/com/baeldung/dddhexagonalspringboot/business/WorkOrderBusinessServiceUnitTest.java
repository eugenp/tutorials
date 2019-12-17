package com.baeldung.dddhexagonalspringboot.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;
import com.baeldung.dddhexagonalspringboot.port.outbound.WorkOrderPort;

@RunWith(MockitoJUnitRunner.class)
public class WorkOrderBusinessServiceUnitTest {

    @InjectMocks
    @Spy
    private WorkOrderBusinessService tested;
    @Mock
    private WorkOrderPort outboundPort;

    @Test
    public void whenCreate_thenWorkOrderPortIsInvoked() {
        when(outboundPort.create(any(WorkOrder.class))).thenReturn(createWorkOrderForTesting());
        Long worOrderId = tested.createWorkOrder();
        assertNotNull(worOrderId);
        assertEquals(12345l, worOrderId.longValue());
        verify(outboundPort, times(1)).create(any(WorkOrder.class));
    }

    @Test
    public void whenGet_thenWorkOrderPortIsInvoked() {
        when(outboundPort.read(anyLong())).thenReturn(createWorkOrderForTesting());
        WorkOrder wo = tested.get(123l);
        assertNotNull(wo);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(outboundPort, times(1)).read(captor.capture());
        assertEquals(123l, captor.getValue().longValue());
    }

    private WorkOrder createWorkOrderForTesting() {
        WorkOrder wo = new WorkOrder();
        wo.setId(12345l);
        wo.setDate(LocalDateTime.now());
        return wo;
    }

}
