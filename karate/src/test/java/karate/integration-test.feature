Feature: integration test

Background: 
  Given url 'http://localhost:' + wiremockPort
  And path 'events'
  And param id = 390
  When method get

Scenario: check for numeric value  
  Then match response.odd.ck == 12.2

Scenario: get success and check for value
  Then status 200
  And match response.id == 390

Scenario: check array contents
  Then match response.odds[*].price contains ['1.30', '5.25', '2.70', '1.20']

Scenario: get and validate entire response
  Then match response == read('classpath:event_0.json')

Scenario: validate response structure
  * def oddSchema = { price: '#string', status: '#? _ < 3', ck: '#number', name: '#regex[0-9X]' }
  * def isValidTime = read('time-validator.js')
  Then match response ==
    """
    { 
      id: '#regex[0-9]+',
      odd: '#(oddSchema)',
      data: { 
        countryId: '#number', 
        countryName: '#string', 
        leagueName: '#string', 
        status: '#number', 
        sportName: '#string',
        time: '#? isValidTime(_)'
      },
      odds: '#array'  
     }
    """
  And match each response.odds == oddSchema
