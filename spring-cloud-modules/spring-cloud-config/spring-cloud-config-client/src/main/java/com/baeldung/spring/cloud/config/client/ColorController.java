package com.baeldung.spring.cloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOError;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RefreshScope
@Controller
@RequestMapping("/thyme")
public class ColorController {

    @Value("${color:blue}")
    private String color;

    @Value("classpath:/templates/color.html")
    private Resource htmlFile;


    @GetMapping("/colorPage")
    @ResponseBody
    public String getColor() throws IOException {
        String htmlContent = StreamUtils.copyToString(htmlFile.getInputStream(), StandardCharsets.UTF_8);
        return  htmlContent.replace("{{color}}", color);
    }
}
