package com.baeldung.hostport;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class GetHostPort {

    public static String getHostWithPort(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        boolean isDefaultPort = ("http".equals(scheme) && serverPort == 80)
                || ("https".equals(scheme) && serverPort == 443);

        if (isDefaultPort) {
            return String.format("%s://%s", scheme, serverName);
        } else {
            return String.format("%s://%s:%d", scheme, serverName, serverPort);
        }
    }

    public static String getBaseUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath(null)
                .build()
                .toUriString();
    }

    public static String getForwardedHost(HttpServletRequest request) {
        String forwardedHost = request.getHeader("X-Forwarded-Host");
        String forwardedProto = request.getHeader("X-Forwarded-Proto");
        String forwardedPort = request.getHeader("X-Forwarded-Port");

        String scheme = forwardedProto != null ? forwardedProto : request.getScheme();
        String host = forwardedHost != null ? forwardedHost : request.getServerName();
        String port = forwardedPort != null ? forwardedPort : String.valueOf(request.getServerPort());

        boolean isDefaultPort = ("http".equals(scheme) && "80".equals(port))
                || ("https".equals(scheme) && "443".equals(port));

        return isDefaultPort ? String.format("%s://%s", scheme, host)
                : String.format("%s://%s:%s", scheme, host, port);
    }
}
