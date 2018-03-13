package cn.nonocast.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.Map;

@Component
@Scope("prototype")
public class Wechat {
    private static final Logger logger = LoggerFactory.getLogger(Wechat.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WechatSettings settings;

    private String token;
    private String openid;

    // SUCCESS:  {access_token=oIoqETOPo2j2PiKg0HxgKr01mw7bIlzN7bMN4pRz-5pmNJQwHHpzQeB5E5Or9A01zUZYuHOLhZlfMim300TxV5-gJXml54hafSeIVIVPLzE, expires_in=7200, refresh_token=m0Lwkb0m-K1PQZZnOGhNEW7263gV60dq1_ISErjto5CjtNNhwdPdWKGk7W5iW7k3ThYbS1ECx5WnUl9z9dFEs-TYfzz1fozJxVddDMe8g2k, openid=ooRguvwjYLGVky0MIIU13D2yMxSc, scope=snsapi_login, unionid=oLwYvwad8ofhOc9yHK1WDbfH0mms}
    // FAILED: {"errcode":40029,"errmsg":"invalid code, hints: [ req_id: eb9CvA0968ns55 ]"}
    public String getUnionid(String code) {
        String unionid = null;

        String template = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        String url = String.format(template, settings.getAppid(), settings.getAppsecret(), code);
        logger.debug(url);

        try {
            Map<String, String> result = objectMapper.readValue(new URL(url), Map.class);
            logger.debug(result.toString());
            if(result.containsKey("unionid")) {
                unionid = result.get("unionid");
                logger.debug("unionid: " + unionid);
                token = result.get("access_token");
                openid = result.get("openid");
            }
        } catch(Exception ex) {
            logger.warn(ex.toString());
        }

        return unionid;
    }

    public String getAppid() {
        return settings.getAppid();
    }

    public Map<String, String> getInfo() {
        Map<String, String> info = null;
        String template = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
        String url = String.format(template, this.token, this.openid);
        try {
            Map<String, String> result = objectMapper.readValue(new URL(url), Map.class);
            logger.debug(result.toString());
            if(result.containsKey("unionid")) {
                info = result;
            }
        } catch(Exception ex) {
            logger.warn(ex.toString());
        }

        return info;
    }
}
