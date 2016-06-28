package io.jsonwebtoken.jjwtfun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home(HttpServletRequest req) {
        String requestUrl = getUrl(req);
        return "Available commands (assumes httpie - https://github.com/jkbrzt/httpie):\n\n" +
            "  http " + requestUrl + "/\n\tThis usage message\n\n" +
            "  http " + requestUrl + "/static-builder\n\tbuild JWT from hardcoded claims\n\n" +
            "  http POST " + requestUrl + "/dynamic-builder-general claim-1=value-1 ... [claim-n=value-n]\n\tbuild JWT from passed in claims (using general claims map)\n\n" +
            "  http POST " + requestUrl + "/dynamic-builder-specific claim-1=value-1 ... [claim-n=value-n]\n\tbuild JWT from passed in claims (using specific claims methods)\n\n" +
            "  http POST " + requestUrl + "/dynamic-builder-compress claim-1=value-1 ... [claim-n=value-n]\n\tbuild DEFLATE compressed JWT from passed in claims\n\n" +
            "  http " + requestUrl + "/parser?jwt=<jwt>\n\tParse passed in JWT\n\n" +
            "  http " + requestUrl + "/parser-enforce?jwt=<jwt>\n\tParse passed in JWT enforcing the 'iss' registered claim and the 'hasMotorcycle' custom claim\n";
    }

    private String getUrl(HttpServletRequest req) {
        return req.getScheme() + "://" +
            req.getServerName() +
            ((req.getServerPort() == 80 || req.getServerPort() == 443) ? "" : ":" + req.getServerPort());
    }
}
