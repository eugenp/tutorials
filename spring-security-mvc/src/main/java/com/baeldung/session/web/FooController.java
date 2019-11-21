package com.baeldung.session.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baeldung.session.bean.Constants;
import com.baeldung.session.bean.Foo;

@RestController
@RequestMapping(path = "/foo")
public class FooController {

    @Autowired
    private Foo theFoo;

    @GetMapping(path = "set")
    public void fooSet(HttpSession session) {
        session.setAttribute(Constants.FOO, new Foo());
    }

    @GetMapping(path = "autowired")
    public Foo getAutowired() {
        return new Foo(theFoo);
    }

    @GetMapping(path = "inject")
    public Foo fooInject(HttpSession session) {
        return (Foo) session.getAttribute(Constants.FOO);
    }

    @GetMapping(path = "raw")
    public Foo fromRaw() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest()
            .getSession(true);
        return (Foo) session.getAttribute(Constants.FOO);
    }
}
