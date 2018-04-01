package com.github.lihongjie.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by lihongjie on 7/21/17.
 *
 *
 */
@Entity(name = "USER_LOGIN_HISTORY")
public class UserLoginHistory {

    @Id
    @Column(name = "USER_LOGIN_ID", length = 250, nullable = false)
    private String userLoginId;
    @Column(name = "VISIT_ID", length = 20)
    private String visitId;
    @Column(name = "FROM_DATE")
    private Date fromDate;
    @Column(name = "THRU_DATE")
    private Date thruDate;
    @Column(name = "PASSWORD_USERD")
    private String passwordUsed;
    @Column(name = "SUCCESSFUL_LOGIN", length = 1)
    private String successfulLogin; // y or n
    @Column(name = "LAST_UPDATED_STAMP")
    private Date lastUpdatedStamp;
    @Column(name = "LAST_UPDATED_TX_STAMP")
    private Date lastUpdatedTxStamp;
    @Column(name = "CREATED_STAMP")
    private Date createdStamp;
    @Column(name = "CREATED_TX_STAMP")
    private Date createdTxStamp;

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getThruDate() {
        return thruDate;
    }

    public void setThruDate(Date thruDate) {
        this.thruDate = thruDate;
    }

    public String getPasswordUsed() {
        return passwordUsed;
    }

    public void setPasswordUsed(String passwordUsed) {
        this.passwordUsed = passwordUsed;
    }

    public String getSuccessfulLogin() {
        return successfulLogin;
    }

    public void setSuccessfulLogin(String successfulLogin) {
        this.successfulLogin = successfulLogin;
    }

    public Date getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(Date lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    public Date getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(Date lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    public Date getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(Date createdStamp) {
        this.createdStamp = createdStamp;
    }

    public Date getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(Date createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
}
