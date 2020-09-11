package main.java.com.baeldung.core.service;

import java.util.Base64;

import org.springframework.stereotype.Service;

import main.java.com.baeldung.core.domain.Message;
import main.java.com.baeldung.port.EncodeService;

@Service
public class EncodeServiceImpl implements EncodeService {

    @Override
    public Message encodeMessage(String textToEncode) {
        Message message = new Message();
        message.setSourceText(textToEncode);
        message.setTargetText(Base64.getEncoder()
            .encodeToString(textToEncode.getBytes()));
        return message;
    }

    @Override
    public Message decodeMessage(String textToDecode) {
        Message message = new Message();
        message.setSourceText(textToDecode);
        byte[] actualByte = Base64.getDecoder()
            .decode(textToDecode);
        String targetString = new String(actualByte);
        message.setTargetText(targetString);
        return message;
    }

}
