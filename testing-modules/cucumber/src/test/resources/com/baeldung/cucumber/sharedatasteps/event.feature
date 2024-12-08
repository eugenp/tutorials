Feature: events are processed

  Scenario: new event is properly initialized
    When new event enters the system
    Then event is properly initialized

  Scenario: event is processed successfully
    Given new event enters the system
    When event processing succeeds
    Then event has COMPLETE status
    And event has processedAt

  Scenario: event is is not processed due to system error
    When new event enters the system
    And event processing fails
    Then event has ERROR status
    And event has processedAt
