package com.baeldung.rest.jbehave;

/**
 * @author aiet
 */
public class GithubUserNotFoundStoryLiveTest extends AbstractStory {

    @Override
    String storyName() {
        return "github_user_not_found.story";
    }

    @Override
    Object stepInstance() {
        return new GithubUserNotFoundSteps();
    }

}
