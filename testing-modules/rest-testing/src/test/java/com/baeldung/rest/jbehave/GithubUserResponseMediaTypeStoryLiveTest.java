package com.baeldung.rest.jbehave;

/**
 * @author aiet
 */
public class GithubUserResponseMediaTypeStoryLiveTest extends AbstractStory {

    @Override
    String storyName() {
        return "github_user_response_mediatype.story";
    }

    @Override
    Object stepInstance() {
        return new GithubUserResponseMediaTypeSteps();
    }

}
