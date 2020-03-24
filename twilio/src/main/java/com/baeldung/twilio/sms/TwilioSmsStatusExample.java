package com.baeldung.twilio.sms;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSmsStatusExample {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "SID";
    public static final String AUTH_TOKEN = "AUTH";

    // Create a phone number in the Twilio console
    public static final String TWILIO_NUMBER = "+12223334444";

    public static void main(String[] args) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        ResourceSet<Message> messages = Message.reader().read();
        for (Message message : messages) {
            System.out.println(message.getSid() + " : " + message.getStatus());
        }
    }
}
