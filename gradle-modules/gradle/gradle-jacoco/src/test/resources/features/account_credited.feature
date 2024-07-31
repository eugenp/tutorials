Feature: Account is credited with amount

  Scenario: Credit amount
    Given account balance is 0.0
    When the account is credited with 10.0
    Then account should have a balance of 10.0
