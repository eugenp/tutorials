package com.baeldung.dddhexagonalspringboot.adapters.secondary.workorder.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;

public class WorkOrderPersistenceAdapterUnitTest {

    @Spy
    @Autowired
    private WorkOrderPersistenceAdapter tested;

    @Mock
    private WorkOrderRepository repository;

    public void whenCreate_thenSaveIsInvoked() {
        when(repository.save(any(WorkOrderDB.class))).thenReturn(createWorkOrderDBForTesting());
        WorkOrder workOrder = tested.create(createWorkOrderForTesting());
        assertEquals(12345l, workOrder.getId()
            .longValue());
        ArgumentCaptor<WorkOrderDB> captor = ArgumentCaptor.forClass(WorkOrderDB.class);
        verify(repository, times(1)).save(captor.capture());
        assertEquals(12345l, captor.getValue()
            .getId()
            .longValue());
    }

    public void whenRead_thenFindByIdIsInvoked() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        WorkOrder wo = tested.read(12345l);
        assertNotNull(wo);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(repository, times(1)).findById(captor.capture());
        assertEquals(12345l, captor.getValue()
            .longValue());
    }

    private WorkOrder createWorkOrderForTesting() {
        WorkOrder wo = new WorkOrder();
        wo.setDate(LocalDateTime.now());
        return wo;
    }

    private WorkOrderDB createWorkOrderDBForTesting() {
        WorkOrderDB woDB = new WorkOrderDB();
        woDB.setId(12345l);
        woDB.setDate(LocalDateTime.now());
        return woDB;
    }

}
