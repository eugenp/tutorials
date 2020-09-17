package main.java.com.baeldung.adapter;

import main.java.com.baeldung.core.domain.Message;
import main.java.com.baeldung.core.service.EncodeServiceImpl;
import main.java.com.baeldung.port.EncodeService;
import main.java.com.baeldung.port.InputPort;

public class ConsoleAdapter implements InputPort {

    private EncodeService encodeService = new EncodeServiceImpl();

    @Override
    public Message getEncodedMessage(String textToEncode) {
        return encodeService.encodeMessage(textToEncode);
    }

    @Override
    public Message getDecodedMessage(String textToDecode) {
        return encodeService.decodeMessage(textToDecode);
    }

}
