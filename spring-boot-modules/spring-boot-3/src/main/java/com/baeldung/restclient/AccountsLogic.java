package com.baeldung.restclient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import ua_parser.Client;
import ua_parser.Parser;

@Service
public class AccountsLogic {

    public Map<String, String> getClientInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");

        Parser uaParser = new Parser();
        Client client = uaParser.parse(userAgent);

        Map<String, String> clientInfo = new HashMap<>();
        clientInfo.put("os_family", client.os.family);
        clientInfo.put("device_family", client.device.family);
        clientInfo.put("userAgent_family", client.userAgent.family);

        return clientInfo;
    }
}
