package main.java.com.baeldung.port;

import main.java.com.baeldung.core.domain.Message;

public interface EncodeService {

    public Message encodeMessage(String textToEncode);

    public Message decodeMessage(String textToEncode);

}
