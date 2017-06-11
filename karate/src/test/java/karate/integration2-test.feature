Feature: integration test 2

Scenario: get and check price for items having status greater thn zero
  Given url 'http://localhost:' + wiremockPort
  And path 'odds'
  When method get
  Then def temp = get response $.odds[?(@.status > 0)].price
  And match temp == [5.25, 1.2]


