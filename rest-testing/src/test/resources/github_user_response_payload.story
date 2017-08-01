Meta:

Narrative:
As a user
I want to look up a valid user's profile on github
So that I can know the login payload should be the same as username

Scenario: when a user checks a valid user's profile on github, github's response json should include a login payload with the same username

Given github user profile api
When I look for eugenp via the api
Then github's response contains a 'login' payload same as eugenp
