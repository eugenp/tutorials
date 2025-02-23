Feature: Time based Greeter
  # Morning
  Scenario: Should greet Good Morning in the morning
    Given the current time is "0700" hours
    When I ask the greeter to greet
    Then I should receive "Good Morning!"
  # Evening
  Scenario: Should greet Good Evening in the evening
    Given the current time is "1900" hours
    When I ask the greeter to greet
    Then I should receive "Good Evening!"
  # Night
  @custom-ignore
  Scenario: Should greet Good Night in the night
    Given the current time is "2300" hours
    When I ask the greeter to greet
    Then I should receive "Good Night!"
  # Midnight
  Scenario: Should greet Good Night at midnight
    Given the current time is "0000" hours
    When I ask the greeter to greet
    Then I should receive "Good Night!"
