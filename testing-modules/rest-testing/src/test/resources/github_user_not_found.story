Meta:

Narrative:
As a user
I want to look up a non-existent user's profile on github
So that I can be sure that the username can not be found on github

Scenario: when a user checks a non-existent user on github, github would respond 'not found'

Given github user profile api
And a random non-existent username
When I look for the random user via the api
Then github respond: 404 not found

When I look for eugenp1 via the api
Then github respond: 404 not found

When I look for eugenp2 via the api
Then github respond: 404 not found
