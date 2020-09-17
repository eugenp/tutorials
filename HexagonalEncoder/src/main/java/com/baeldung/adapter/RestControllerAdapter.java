package main.java.com.baeldung.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.com.baeldung.core.domain.Message;
import main.java.com.baeldung.core.service.EncodeServiceImpl;
import main.java.com.baeldung.port.InputPort;

@RestController
public class RestControllerAdapter implements InputPort {

    @Autowired
    private EncodeServiceImpl encodeService;

    @Override
    @RequestMapping(value = "/encode", method = RequestMethod.POST)
    public Message getEncodedMessage(String message) {
        System.out.println("message = " + message);
        Message encodedMessage = encodeService.encodeMessage(message);
        return encodedMessage;
    }

    @Override
    @RequestMapping(value = "/decode", method = RequestMethod.POST)
    public Message getDecodedMessage(String message) {
        System.out.println("message = " + message);
        Message decodedMessage = encodeService.decodeMessage(message);
        return decodedMessage;
    }

}
