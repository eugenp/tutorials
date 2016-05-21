/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.springintegration.dao;


/**
 * @author Tayo
 */
public abstract class IUserManagementDAO implements UserManagementDAO {


    @Override
    public abstract boolean createUser(String userName);


}
