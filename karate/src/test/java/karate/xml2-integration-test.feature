Feature: xml integration test

Scenario: check subjects for science teacher via xpath
  Given url 'http://localhost:' + wiremockPort
  And path 'teachers'
  When method get
  And match //teacher[@department='science']/subject contains ['math', 'physics']
