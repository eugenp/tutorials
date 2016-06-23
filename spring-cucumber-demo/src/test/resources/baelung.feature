Feature: the poop can be retrieved
#  Scenario: client makes call to GET /loop
#    When the client calls /loop
#    Then the client receives status code of 200
#    And the client receives server version loop
  Scenario: client makes call to GET /hello
    Given the client calls /hello
    When the client receives status code of 200
    Then the client receives server version hello