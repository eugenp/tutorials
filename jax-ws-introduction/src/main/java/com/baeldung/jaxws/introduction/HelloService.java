package com.baeldung.jaxws.introduction;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by dreamAdmin on 08/02/2017.
 */
@WebService
public interface HelloService {

    @WebMethod
    String getGreeting(String name);
}
