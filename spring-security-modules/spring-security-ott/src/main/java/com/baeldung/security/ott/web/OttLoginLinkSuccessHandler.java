package com.baeldung.security.ott.web;

import com.baeldung.security.ott.service.OttSenderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class OttLoginLinkSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    private final OttSenderService senderService;
    private final OneTimeTokenGenerationSuccessHandler redirectHandler = new RedirectOneTimeTokenGenerationSuccessHandler("/login/ott");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {

        senderService.sendTokenToUser(oneTimeToken.getUsername(),oneTimeToken.getTokenValue(),oneTimeToken.getExpiresAt());
        redirectHandler.handle(request, response, oneTimeToken);
    }
}
