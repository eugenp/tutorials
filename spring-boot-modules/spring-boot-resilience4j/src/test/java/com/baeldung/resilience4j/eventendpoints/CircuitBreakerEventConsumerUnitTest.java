package com.baeldung.resilience4j.eventendpoints;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSuccessEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CircuitBreakerEventConsumerUnitTest {

    private CircuitBreaker circuitBreaker;
    private CircuitBreaker.EventPublisher eventPublisher;

    @Mock
    private Logger mockLogger;

    @Captor
    private ArgumentCaptor<String> logMessageCaptor;

    @Captor
    private ArgumentCaptor<CircuitBreakerEvent> eventCaptor;

    @BeforeEach
    void setUp() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
          .failureRateThreshold(50)
          .slidingWindowSize(5)
          .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        circuitBreaker = registry.circuitBreaker("testService");
        eventPublisher = circuitBreaker.getEventPublisher();
    }

    @Test
    void givenSuccessEventConsumer_whenSuccessfulCallExecuted_thenSuccessEventIsPublished() {
        // Arrange
        List<CircuitBreakerEvent> capturedEvents = new ArrayList<>();
        eventPublisher.onSuccess(capturedEvents::add);

        // Act
        String result = circuitBreaker.executeSupplier(() -> "success");

        // Assert
        assertEquals(1, capturedEvents.size());
        assertInstanceOf(CircuitBreakerOnSuccessEvent.class, capturedEvents.get(0));
        assertEquals("success", result);
    }

    @Test
    void givenErrorEventConsumer_whenFailingCallExecuted_thenErrorEventIsPublished() {
        // Arrange
        List<CircuitBreakerEvent> capturedEvents = new ArrayList<>();
        eventPublisher.onError(capturedEvents::add);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
          circuitBreaker.executeSupplier(() -> {
              throw new RuntimeException("Test error");
          })
        );

        assertEquals(1, capturedEvents.size());
        assertInstanceOf(CircuitBreakerOnErrorEvent.class, capturedEvents.get(0));
        assertEquals("Test error", exception.getMessage());
    }

    @Test
    void givenFailureRateExceededConsumer_whenConsumerRegistered_thenPublisherAcceptsRegistration() {
        // Arrange
        List<CircuitBreakerEvent> capturedEvents = new ArrayList<>();

        // Act
        eventPublisher.onFailureRateExceeded(capturedEvents::add);

        // Assert
        assertNotNull(eventPublisher);
    }

    @Test
    void givenStateTransitionConsumer_whenConsumerRegistered_thenPublisherAcceptsRegistration() {
        // Arrange
        List<CircuitBreakerEvent> capturedEvents = new ArrayList<>();

        // Act
        eventPublisher.onStateTransition(capturedEvents::add);

        // Assert
        assertNotNull(eventPublisher);
    }

    @Test
    void givenResetEventConsumer_whenConsumerRegistered_thenPublisherAcceptsRegistration() {
        // Arrange
        List<CircuitBreakerEvent> capturedEvents = new ArrayList<>();

        // Act
        eventPublisher.onReset(capturedEvents::add);

        // Assert
        assertNotNull(eventPublisher);
    }

    @Test
    void givenMultipleEventConsumers_whenAllConsumersRegistered_thenAllRegistrationsAccepted() {
        // Arrange
        List<CircuitBreakerEvent> successEvents = new ArrayList<>();
        List<CircuitBreakerEvent> errorEvents = new ArrayList<>();
        List<CircuitBreakerEvent> stateTransitionEvents = new ArrayList<>();

        // Act
        eventPublisher
          .onSuccess(successEvents::add)
          .onError(errorEvents::add)
          .onStateTransition(stateTransitionEvents::add);

        // Assert
        assertNotNull(eventPublisher);
    }

    @Test
    void givenNullConsumers_whenRegisteringEventConsumers_thenNoExceptionThrown() {
        // Arrange & Act & Assert
        assertDoesNotThrow(() -> {
            eventPublisher
              .onSuccess(null)
              .onError(null)
              .onIgnoredError(null);
        });

        String result = circuitBreaker.executeSupplier(() -> "test");
        assertEquals("test", result);
    }

    @Test
    void givenEventPublisher_whenChainingMultipleConsumerRegistrations_thenSamePublisherInstanceReturned() {
        // Arrange & Act
        CircuitBreaker.EventPublisher publisher = eventPublisher
          .onSuccess(event -> {
          })
          .onError(event -> {
          })
          .onIgnoredError(event -> {
          })
          .onCallNotPermitted(event -> {
          })
          .onFailureRateExceeded(event -> {
          })
          .onSlowCallRateExceeded(event -> {
          })
          .onStateTransition(event -> {
          })
          .onReset(event -> {
          });

        // Assert
        assertSame(eventPublisher, publisher);
    }

    @Test
    void givenMultipleEventConsumers_whenDifferentEventsTriggered_thenCorrespondingEventsAreCaptured() {
        // Arrange
        List<String> eventTypes = new ArrayList<>();

        eventPublisher.onSuccess(event -> eventTypes.add("SUCCESS"));
        eventPublisher.onError(event -> eventTypes.add("ERROR"));
        eventPublisher.onStateTransition(event -> eventTypes.add("STATE_TRANSITION"));

        // Act
        circuitBreaker.executeSupplier(() -> "success");
        try {
            circuitBreaker.executeSupplier(() -> {
                throw new RuntimeException();
            });
        } catch (Exception ignored) {
        }

        // Assert
        assertTrue(eventTypes.contains("SUCCESS"));
        assertTrue(eventTypes.contains("ERROR"));
    }

    @Test
    void givenCircuitBreakerInstance_whenGettingEventPublisher_thenNotNullPublisherReturned() {
        // Arrange & Act
        CircuitBreaker.EventPublisher publisher = circuitBreaker.getEventPublisher();

        // Assert
        assertNotNull(publisher);
        assertSame(eventPublisher, publisher);
    }

    @Test
    void givenSuccessEventConsumer_whenMultipleSuccessfulCallsExecuted_thenAllSuccessEventsArePublished() {
        // Arrange
        List<CircuitBreakerEvent> events = new ArrayList<>();
        eventPublisher.onSuccess(events::add);

        // Act
        circuitBreaker.executeSupplier(() -> "first");
        circuitBreaker.executeSupplier(() -> "second");
        circuitBreaker.executeSupplier(() -> "third");

        // Assert
        assertEquals(3, events.size());
        events.forEach(event ->
          assertInstanceOf(CircuitBreakerOnSuccessEvent.class, event)
        );
    }

    @Test
    void givenCallNotPermittedConsumer_whenConsumerRegistered_thenPublisherAcceptsRegistration() {
        // Arrange
        List<CircuitBreakerEvent> capturedEvents = new ArrayList<>();

        // Act
        eventPublisher.onCallNotPermitted(capturedEvents::add);

        // Assert
        assertNotNull(eventPublisher);
    }

    @Test
    void givenSlowCallRateExceededConsumer_whenConsumerRegistered_thenPublisherAcceptsRegistration() {
        // Arrange
        List<CircuitBreakerEvent> capturedEvents = new ArrayList<>();

        // Act
        eventPublisher.onSlowCallRateExceeded(capturedEvents::add);

        // Assert
        assertNotNull(eventPublisher);
    }

    @Test
    void givenMultipleConsumersForSameEventType_whenEventPublished_thenAllConsumersReceiveEvent() {
        // Arrange
        List<String> consumerMessages = new ArrayList<>();

        eventPublisher.onSuccess(event -> consumerMessages.add("consumer1"));
        eventPublisher.onSuccess(event -> consumerMessages.add("consumer2"));

        // Act
        circuitBreaker.executeSupplier(() -> "test");

        // Assert
        assertEquals(2, consumerMessages.size());
        assertTrue(consumerMessages.contains("consumer1"));
        assertTrue(consumerMessages.contains("consumer2"));
    }
}