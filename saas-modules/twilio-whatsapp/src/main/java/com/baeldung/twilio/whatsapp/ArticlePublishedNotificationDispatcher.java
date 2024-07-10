package com.baeldung.twilio.whatsapp;

import org.json.JSONObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(TwilioConfigurationProperties.class)
public class ArticlePublishedNotificationDispatcher {

    private final TwilioConfigurationProperties twilioConfigurationProperties;

    public void dispatch(String phoneNumber, String articleUrl) {
        String messagingSid = twilioConfigurationProperties.getMessagingSid();
        String contentSid = twilioConfigurationProperties.getNewArticleNotification().getContentSid();
        PhoneNumber toPhoneNumber = new PhoneNumber(String.format("whatsapp:%s", phoneNumber));

        JSONObject contentVariables = new JSONObject();
        contentVariables.put("ArticleURL", articleUrl);
        
        Message.creator(toPhoneNumber, messagingSid, "")
          .setContentSid(contentSid)
          .setContentVariables(contentVariables.toString())
          .create();
    }

}
