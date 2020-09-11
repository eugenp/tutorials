package main.java.com.baeldung.port;

import main.java.com.baeldung.core.domain.Message;

public interface RestControllerPort {

    public Message getEncodedMessage(String textToEncode);

    public Message getDecodedMessage(String textToDecode);

}
