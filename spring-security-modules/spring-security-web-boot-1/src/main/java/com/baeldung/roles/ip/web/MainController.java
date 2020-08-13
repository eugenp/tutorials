package com.baeldung.roles.ip.web;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import com.baeldung.roles.custom.persistence.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter springSecurityFilterChain;

    @RequestMapping(method = RequestMethod.GET, value = "/filters")
    @ResponseBody
    public void getFilters() {
        FilterChainProxy filterChainProxy = (FilterChainProxy) springSecurityFilterChain;
        List<SecurityFilterChain> list = filterChainProxy.getFilterChains();
        list.stream()
              .flatMap(chain -> chain.getFilters().stream())
              .forEach(filter -> System.out.println(filter.getClass()));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable final long id, HttpServletRequest request) {
        return new Foo("Sample");
    }

}
