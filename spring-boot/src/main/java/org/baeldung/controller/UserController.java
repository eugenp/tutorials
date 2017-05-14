package org.baeldung.controller;

import org.baeldung.domain.ResponseVO;
import org.baeldung.domain.UserVO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("/helloGet")
    public String hello(Model model, @RequestParam(value = "home", required = false, defaultValue = "World") String name) {
        return "hello";
    }

    @RequestMapping(value = "/aJsonPostMethod", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO aJsonPostMethod(@RequestBody UserVO userVo) {
        ResponseVO respVo = new ResponseVO();
        if (userVo.getName().equals("seema")) {
            respVo.setSuccess(true);
        } else {
            respVo.setSuccess(false);
        }
        return respVo;
    }
}
