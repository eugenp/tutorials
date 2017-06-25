Meta:

Narrative:
As a user
I want to look up a valid user's profile on github
So that I can know that github responds data of type json

Scenario: when a user checks a valid user's profile on github, github would respond json data

Given github user profile api
And a valid username
When I look for the user via the api
Then github respond data of type json