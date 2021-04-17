package com.baeldung.hexagonalarchitecture1.recordscheduler;

import com.baeldung.hexagonalarchitecture1.recordscheduler.application.RecordSchedulerService;
import com.baeldung.hexagonalarchitecture1.recordscheduler.application.port.out.AuthorizeRecordRequest;
import com.baeldung.hexagonalarchitecture1.recordscheduler.application.port.out.UpdateRecordSchedule;
import com.baeldung.hexagonalarchitecture1.recordscheduler.domain.RecordSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class RecordSchedulerServiceTest {
    @Mock
    private AuthorizeRecordRequest authorizeRecordRequest;

    @Mock
    private UpdateRecordSchedule updateRecordSchedule;

    @InjectMocks
    private RecordSchedulerService recordSchedulerService;

    @Test
    public void whenAuthorizationSucceedsRequestIsStored(){
        Mockito.when(authorizeRecordRequest.isRecordingAllowed(Mockito.any(RecordSchedule.class))).thenReturn(true);
        boolean result = recordSchedulerService.recordScheduleRequest(new RecordSchedule("userId", "programId", null, null));
        Mockito.verify(authorizeRecordRequest, times(1)).isRecordingAllowed(Mockito.any(RecordSchedule.class));
        Mockito.verify(updateRecordSchedule, times(1)).upsertSchedule(Mockito.any(RecordSchedule.class));
        Assertions.assertTrue(result);
    }

    @Test
    public void WhenAuthorizationFailsRequestIsRejected(){
        Mockito.when(authorizeRecordRequest.isRecordingAllowed(Mockito.any(RecordSchedule.class))).thenReturn(false);
        boolean result = recordSchedulerService.recordScheduleRequest(new RecordSchedule("userId", "programId", null, null));
        Mockito.verify(authorizeRecordRequest, times(1)).isRecordingAllowed(Mockito.any(RecordSchedule.class));
        Mockito.verify(updateRecordSchedule, times(0)).upsertSchedule(Mockito.any(RecordSchedule.class));
        Assertions.assertFalse(result);
    }
}
