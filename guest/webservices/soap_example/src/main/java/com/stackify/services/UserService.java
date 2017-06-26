package com.stackify.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.stackify.models.User;
import com.stackify.models.Users;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface UserService {

    @WebMethod
    public void addUser(User user);

    @WebMethod
    public Users getUsers();
}
