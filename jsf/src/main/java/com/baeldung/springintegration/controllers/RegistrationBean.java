package com.baeldung.springintegration.controllers;

import com.baeldung.springintegration.dao.IUserManagementDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@ManagedBean(name = "registration")
@ViewScoped
public class RegistrationBean implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationBean.class);

    @ManagedProperty(value = "#{userManagementDAO}")
    transient private IUserManagementDAO theUserDao;
    private String userName;
    private String operationMessage;

    /**
     * Creates a new instance of RegistrationBean
     */
    public RegistrationBean() {
    }

    public String createNewUser() {
        try {
            LOGGER.info("Creating new user");
            FacesContext context = FacesContext.getCurrentInstance();
            boolean operationStatus = theUserDao.createUser(userName);
            context.isValidationFailed();
            if (operationStatus) {
                operationMessage = "User " + userName + " created";
            }
        } catch (Exception ex) {
            LOGGER.error("Error registering new user ");
            ex.printStackTrace();
        }
        return null;
    }

    public String getUserName() {
        return userName;
    }

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

    public String getOperationMessage() {
        return operationMessage;
    }

    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }
}
