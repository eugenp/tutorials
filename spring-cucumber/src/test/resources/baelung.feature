Feature: the message can be retrieved
  Scenario: client makes call to POST /baeldung
    When the client calls /baeldung
    Then the client receives status code of 200
    And the client receives server version hello
  Scenario: client makes call to GET /hello
    Given the client calls /hello
    When the client receives status code of 200
    Then the client receives server version hello