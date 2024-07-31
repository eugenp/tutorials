Feature: Shopping

	Scenario: Track my budget 
		Given I have 100 in my wallet
		When I buy milk with 10
		Then I should have 90 in my wallet
    
	Scenario: Track my budget 
		Given I have 200 in my wallet
		When I buy rice with 20
		Then I should have 180 in my wallet    
