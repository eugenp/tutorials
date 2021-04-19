@ui
Feature: UI - Random Number Generator

  Scenario: Successfully generate a random number
    Given we are expecting a random number between min and max
    And I am on random-number-generator page
    When I enter min 1
    And I enter max 10
    And I press Generate button
    Then I should receive a random number between 1 and 10