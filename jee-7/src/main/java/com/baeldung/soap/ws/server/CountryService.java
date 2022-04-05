package com.baeldung.soap.ws.server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style=Style.RPC)
public interface CountryService {
    
    @WebMethod
    Country findByName(String name);

}
