/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.springintegration.controllers;

import com.baeldung.springintegration.dao.IUserManagementDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author Tayo
 */
@ManagedBean(name = "registration")
@ViewScoped
public class RegistrationBean implements Serializable {

    @ManagedProperty(value = "#{userManagementDAO}")
    transient private IUserManagementDAO theUserDao;
    private String userName;
    private String operationMessage;
    private boolean operationStatus;

    /**
     * Creates a new instance of RegistrationBean
     */
    public RegistrationBean() {
    }

    public String createNewUser() {
        try {
            Logger.getAnonymousLogger().info("Creating new user");
            FacesContext context = FacesContext.getCurrentInstance();
            operationStatus = theUserDao.createUser(userName); //DAO layer is used to register user using gathered data
            context.isValidationFailed();
            if (operationStatus) {

                operationMessage = "User " + userName + " created";
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().severe("Error registering new user ");
            ex.printStackTrace();
        }
        return null;
    }

    public String returnHome() {
        return "home";
    }

    /**
     * @return the name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param theUserDao the theUserDao to set
     */
    public void setTheUserDao(IUserManagementDAO theUserDao) {
        this.theUserDao = theUserDao;
    }

    public IUserManagementDAO getTheUserDao() {
        return this.theUserDao;
    }

    /**
     * @return the operationMessage
     */
    public String getOperationMessage() {
        return operationMessage;
    }

    /**
     * @param operationMessage the operationMessage to set
     */
    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }
}
