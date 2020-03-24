package com.baeldung.rest.jbehave;

/**
 * @author aiet
 */
public class GithubUserResponsePayloadStoryLiveTest extends AbstractStory {

    @Override
    String storyName() {
        return "github_user_response_payload.story";
    }

    @Override
    Object stepInstance() {
        return new GithubUserResponsePayloadSteps();
    }

}
