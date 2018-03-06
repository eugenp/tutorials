package cn.nonocast.social;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class WechatLoader {
    private static final Logger logger = LoggerFactory.getLogger(WechatLoader.class);

    @Autowired
    private WechatSettings settings;

    @Autowired
    private ObjectMapper objectMapper;

    public void load(String path) {
        try {
            Path p = Paths.get(path, "wechat.json");
            String content = new String(Files.readAllBytes(p));
            TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};
            Map<String,String> settings = objectMapper.readValue(content, typeRef);

            this.settings.setAppid(settings.get("appid"));
            this.settings.setAppsecret(settings.get("appsecret"));
        }catch(IOException ex) {
            logger.warn(ex.toString());
        }
    }
}
