import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;

public class TwilioSmsMediaExample {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "YOUR_ACCOUNT_SID";
    public static final String AUTH_TOKEN  = "YOUR_ACCOUNT_TOKEN";

    // Create a phone number in the Twilio console
    public static final String TWILIO_NUMBER = "+13334445555";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+12227779999"),
                new PhoneNumber(TWILIO_NUMBER),
                "Sample Twilio MMS using Java")
                .setMediaUrl(
                        Promoter.listOfOne(
                                URI.create("http://www.baeldung.com/wp-content/uploads/2017/10/icon-javaseries-home.png")))
                .create();
    }
}
