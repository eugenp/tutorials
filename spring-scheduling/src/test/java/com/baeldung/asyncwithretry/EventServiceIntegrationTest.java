package com.baeldung.asyncwithretry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AsyncConfig.class}, loader = AnnotationConfigContextLoader.class)
class EventServiceIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceIntegrationTest.class);

    @Autowired
    private EventService asyncEventService;

    @MockBean
    private DownstreamService downstreamService;

    @Test
    void givenAsyncMethodHasNoException_whenAsyncMethodIscalled_thenReturnSuccess_WithoutAnyRetry() throws Exception {
        LOGGER.info("Testing for async with retry execution with thread " + Thread.currentThread().getName());
        when(downstreamService.publishEvents(anyList())).thenReturn(true);
        Future<String> resultFuture = asyncEventService.processEvents(List.of("test1"));

        while (!resultFuture.isDone() && !resultFuture.isCancelled()) {
            TimeUnit.SECONDS.sleep(5);
        }

        assertTrue(resultFuture.isDone());
        assertEquals("Completed", resultFuture.get());
        verify(downstreamService, times(1)).publishEvents(anyList());
    }

    @Test
    void givenAsyncMethodHasRuntimeException_whenCalledAsyncMethod_thenReturnFailed_With_MultipleRetries() throws InterruptedException {
        LOGGER.info("Testing for async with retry execution with thread " + Thread.currentThread().getName());
        when(downstreamService.publishEvents(anyList())).thenThrow(RuntimeException.class);
        Future<String> futureResult = asyncEventService.processEvents(List.of("test1"));

        while (!futureResult.isDone() && !futureResult.isCancelled()) {
            TimeUnit.SECONDS.sleep(5);
        }

        assertTrue(futureResult.isDone());
        assertThrows(ExecutionException.class, futureResult::get);
        verify(downstreamService, times(4)).publishEvents(anyList());
    }

    @Test
    void givenAsyncMethodHasServiceUnavailableException_whenCalledAsyncMethod_thenReturnFailed_With_MultipleRetries() throws InterruptedException {
        LOGGER.info("Testing for async with retry execution with thread " + Thread.currentThread().getName());
        when(downstreamService.publishEvents(anyList())).thenThrow(HttpServerErrorException.ServiceUnavailable.class);
        Future<String> futureResult = asyncEventService.processEvents(List.of("test1"));

        while (!futureResult.isDone() && !futureResult.isCancelled()) {
            TimeUnit.SECONDS.sleep(5);
        }

        assertTrue(futureResult.isDone());
        assertThrows(ExecutionException.class, futureResult::get);
        verify(downstreamService, times(4)).publishEvents(anyList());
    }

    @Test
    void givenAsyncMethodHasRuntimeExceptionOnce_whenCalledAsyncMethod_thenReturnFailed_With_MultipleRetries() throws Exception {
        LOGGER.info("Testing for async with retry execution with thread " + Thread.currentThread().getName());
        when(downstreamService.publishEvents(anyList())).thenThrow(RuntimeException.class).thenReturn(true);
        Future<String> futureResult = asyncEventService.processEvents(List.of("test1"));

        while (!futureResult.isDone() && !futureResult.isCancelled()) {
            TimeUnit.SECONDS.sleep(5);
        }

        assertTrue(futureResult.isDone());
        assertEquals("Completed", futureResult.get());
        verify(downstreamService, times(2)).publishEvents(anyList());
    }
}
