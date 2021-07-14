@api
Feature: Health check

  Scenario: Should have a working health check
    When I make a GET call on /status
    Then I should receive 200 response status code
    And should receive a non-empty body