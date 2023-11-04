package com.baeldung.asyncwithretry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AsyncConfig.class}, loader = AnnotationConfigContextLoader.class)
class AsyncEventServiceUnitTest {

    @Autowired
    private AsyncEventService asyncEventService;

    @MockBean
    private DownstreamService downstreamService;

    @Test
    void givenAsyncMethodHasErrors_whenAsyncMethodIscalled_thenReturnSuccess_WithoutAnyRetry() throws InterruptedException, ExecutionException {
        when(downstreamService.publishEvents(anyList())).thenReturn(true);
        Future<String> resultFuture = asyncEventService.processEvents(List.of("test1"));

        while (!resultFuture.isDone() && !resultFuture.isCancelled()) {
            System.out.println("Wait for future completion");
        }

        assertTrue(resultFuture.isDone());
        assertEquals("Completed", resultFuture.get());
        verify(downstreamService, times(1)).publishEvents(anyList());
    }

    @Test
    void givenAsyncMethodHasError_whenCalledAsyncMethod_thenReturnFailed_With_MultipleRetries() {
        when(downstreamService.publishEvents(anyList())).thenThrow(RuntimeException.class);
        Future<String> resultFuture = asyncEventService.processEvents(List.of("test1"));

        while (!resultFuture.isDone() && !resultFuture.isCancelled()) {
            System.out.println("Wait for future completion");
        }

        assertTrue(resultFuture.isDone());
        assertThrows(ExecutionException.class, resultFuture::get);
        verify(downstreamService, times(2)).publishEvents(anyList());
    }
}
