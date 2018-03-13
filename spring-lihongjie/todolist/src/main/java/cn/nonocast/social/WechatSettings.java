package cn.nonocast.social;

import org.springframework.stereotype.Component;

@Component
public class WechatSettings {
    private String appid;
    private String appsecret;

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
