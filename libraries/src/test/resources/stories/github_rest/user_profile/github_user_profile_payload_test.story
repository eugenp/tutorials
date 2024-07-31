
Meta:

Narrative:
As a user
I want to look up a valid user's profile on github
So that I can know the login payload should be the same as username

Scenario: Github user's profile should have a login payload same as username

Given github user profile api
When looking for eugenp via the api
Then github's response contains a 'login' payload same as eugenp
