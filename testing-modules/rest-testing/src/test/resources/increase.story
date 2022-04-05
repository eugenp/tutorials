JBehave Story - An increase test

Meta:

Narrative:
As a user
I want to increase a counter
So that I can have the counter's value increase by 1

Scenario: when a user increases a counter, its value is increased by 1

Given a counter
And the counter has any integral value
When the user increases the counter
Then the value of the counter must be 1 greater than previous value