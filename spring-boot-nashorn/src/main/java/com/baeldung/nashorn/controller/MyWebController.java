package com.baeldung.nashorn.controller;

import java.io.FileReader;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyWebController {

    @RequestMapping("/")
    public String index(Map<String, Object> model) throws Exception {

        ScriptEngine nashorn = new ScriptEngineManager().getEngineByName("nashorn");

        nashorn.eval(new FileReader("./src/main/webapp/js/react.js"));
        nashorn.eval(new FileReader("./src/main/webapp/js/react-dom-server.js"));

        nashorn.eval(new FileReader("./src/main/webapp/app.js"));
        Object html = nashorn.eval("ReactDOMServer.renderToString(" + "React.createElement(App, {data: [0,1,1]})" + ");");

        model.put("content", String.valueOf(html));
        return "index";
    }
}
