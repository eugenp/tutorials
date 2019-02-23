package com.baeldung.spring.cloud.kubernetes.services.client.controller;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.baeldung.spring.cloud.kubernetes.services.client.config.ClientConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RefreshScope
public class ClientController {

    private static final Log log = LogFactory.getLog(ClientController.class);

    private enum ClientTravelType {
        BUSINESS,
        STUDENT,
        COUPLE,
        FRIENDS,
        SINGLE,
        FAMILY;
    }

    @Autowired
    private ClientConfig clientConfig;

    @RequestMapping(method = GET)
    public String get() throws UnknownHostException, UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Host: ").append(InetAddress.getLocalHost().getHostName()).append("<br/>");
        stringBuilder.append("Client Type: ").append(clientConfig.getType()).append("<br/>");
        stringBuilder.append("IP: ").append(InetAddress.getLocalHost().getHostAddress()).append("<br/>");
        return stringBuilder.toString();
    }
}
