package org.baeldung.controller;

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
    public String hello(Model model, @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return "hello";
    }

    @RequestMapping(value = "/helloPost", method = RequestMethod.POST)
    public String helloPost(Model model, @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return "helloPost";
    }

    @RequestMapping(value = "/aJsonPostMethod", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO aJsonPostMethod(@RequestBody UserVO userVo) {
        ResponseVO respVo = new ResponseVO();
        if(userVo.getName().equals("tousif"))
        {
                respVo.setSuccess(true);
                respVo.setMessage("User is Authenticated");
        }
        else 
        {
                respVo.setSuccess(false);
                respVo.setMessage("User is Unauthenticated");
        }
        return respVo;
    }
}
