package com.baeldung.jaxws.introduction;

import javax.jws.WebService;

/**
 * Created by dreamAdmin on 08/02/2017.
 */
@WebService(endpointInterface = "com.baeldung.jaxws.introduction.HelloService")
public class HelloServiceImpl implements HelloService {

    public String getGreeting(String name) {
        return "Hello " + name;
    }
}
