Feature: Time-based Greeter
  Scenario: Should greet Good Morning in the morning
    Given the current time is "0700" hours
    When I ask the greeter to greet
    Then I should receive "Good Morning!"
