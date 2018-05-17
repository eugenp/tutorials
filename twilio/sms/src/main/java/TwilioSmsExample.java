import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSmsExample {
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
                "Sample Twilio SMS using Java")
                .create();

    }
}
