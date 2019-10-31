package com.baeldung.chainofresponsibility;

public class OAuthAuthenticationProcessor extends AuthenticationProcessor {

    public OAuthAuthenticationProcessor(AuthenticationProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public boolean isAuthorized(AuthenticationProvider authProvider) {

        if (authProvider instanceof OAuthTokenProvider) {
            return Boolean.TRUE;
        } else if (nextProcessor != null) {
            return nextProcessor.isAuthorized(authProvider);
        } else {
            return Boolean.FALSE;
        }
    }

}
