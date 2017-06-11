Feature: xml integration test

Background: 
  Given url 'http://localhost:' + wiremockPort
  And path 'employees'
  And request ''
  When method post  

Scenario: check for single value via xpath
  Then match /employees/employee/first-name == 'Jane'
  
Scenario: check for multiple values via xpath
  Then match /employees/employee/first-name == 'Jane'
  And match /employees/employee/last-name == 'Daisy'
  And match /employees/employee/sex == 'f'

Scenario: check for nested xml
  Then match /employees/employee ==
  """
  <employee category="skilled">
    <first-name>Jane</first-name>
    <last-name>Daisy</last-name>
    <sex>f</sex>
  </employee>  
  """

Scenario: check that value contains
  Then match /employees/employee/first-name contains 'Ja'

Scenario: check using xpath function
  Then match /employees/employee/first-name[text()='Jane'] == '#notnull'
