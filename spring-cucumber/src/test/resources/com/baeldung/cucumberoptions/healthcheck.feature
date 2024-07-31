Feature: healthcheck endpoints can be verified

  @v1
  Scenario: v1 status is healthy
    When the client calls /v1/status
    Then the client receives 200 status code

  @v2
  Scenario: v2 status is healthy
    When the client calls /v2/status
    Then the client receives 200 status code
