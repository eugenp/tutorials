package com.baeldung.springai.advisors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;

public class CustomLoggingAdvisor implements CallAroundAdvisor {
    private final static Logger logger = LoggerFactory.getLogger(CustomLoggingAdvisor.class);

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {

        advisedRequest = this.before(advisedRequest);

        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);

        this.observeAfter(advisedResponse);

        return advisedResponse;
    }

    private void observeAfter(AdvisedResponse advisedResponse) {
        logger.info(advisedResponse.response()
          .getResult()
          .getOutput()
          .getContent());

    }

    private AdvisedRequest before(AdvisedRequest advisedRequest) {
        logger.info(advisedRequest.userText());
        return advisedRequest;
    }

    @Override
    public String getName() {
        return "CustomLoggingAdvisor";
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
