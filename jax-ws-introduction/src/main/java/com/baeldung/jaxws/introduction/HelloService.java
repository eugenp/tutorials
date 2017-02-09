package com.baeldung.jaxws.introduction;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by Eunice A. Obugyei on 08/02/2017.
 */
@WebService
public interface HelloService {

    @WebMethod
    String getGreeting(String name);
}
