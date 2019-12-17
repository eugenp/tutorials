package com.baeldung.dddhexagonalspringboot.adapters.primary.client;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.baeldung.dddhexagonalspringboot.port.inbound.ClientPort;

@RunWith(MockitoJUnitRunner.class)
public class ClientAdapterUnitTest {
    
    @Spy
    @InjectMocks
    private ClientAdapter tested;
    
    @Mock
    private ClientPort clientPort;
    
    @Test
    public void whenGet_thenGetIsInvoked() {
        when(clientPort.get(anyLong())).thenReturn(createWorkOrderForTesting());
        WorkOrderClientDTO dto = tested.get(12345l);
        assertNotNull(dto);
        assertEquals(12345l, dto.getId().longValue());
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(clientPort, times(1)).get(captor.capture());
        assertEquals(12345l, captor.getValue().longValue());
    }
    
    @Test
    public void whenCreate_thenCreateIsInvoved() {
        when(clientPort.createWorkOrder()).thenReturn(12345l);
        Long id = tested.create();
        assertEquals(12345l, id.longValue());
        verify(clientPort, times(1)).createWorkOrder();
    }
    
    
    private WorkOrder createWorkOrderForTesting() {
        WorkOrder wo = new WorkOrder();
        wo.setId(12345l);
        wo.setDate(LocalDateTime.now());
        return wo;
    }

}
