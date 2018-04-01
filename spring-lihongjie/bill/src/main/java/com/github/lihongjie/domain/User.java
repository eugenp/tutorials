package com.github.lihongjie.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by lihongjie on 7/14/17.
 *
 *
 */
@Entity(name = "USER_LOGIN")
public class User {

    @Id
    @Column(name = "USER_LOGIN_ID", nullable = false)
    private String id;
    @Column(name = "USERNAME", length = 100)
    private String username;
    @Column(name = "SALUTATION", length = 100)
    private String salutation;
    @Column(name = "EMAIL", length = 100)
    private String email;
    @Column(name = "GENDER", length = 1)
    private String gender;
    @Column(name = "CURRENT_PASSWORD", length = 60)
    private String currentPassword;
    @Column(name = "HEIGHT")
    private double height;
    @Column(name = "WEIGHT")
    private double weight;
    @Column(name = "BIRTH_DATE")
    private Date birthDate;
    @Column(name = "MARITAL_STATUS", length = 1)
    private String maritalStatus;
    @Column(name = "IS_SYSTEM", length = 1)
    private String isSystem;
    @Column(name = "ENABLED", length = 1)
    private String enabled;
    @Column(name = "HAS_LOGGED_OUT", length = 1)
    private String hasLoggedOut;
    @Column(name = "LAST_CURRENCY_UOM", length = 20)
    private String lastCurrencyUom;
    @Column(name = "LAST_LOCALE", length = 10)
    private String lastLocale;
    @Column(name = "LAST_TIME_ZONE", length = 60)
    private String lastTimeZone;
    @Column(name = "DISABLED_DATE_TIME")
    private Date disabledDateTime;
    @Column(name = "SUCCESSIVE_FAILED_LOGINS", length = 20)
    private long successiveFailedLogins;
    @Column(name = "EXTERNAL_AUTH_ID", length = 250)
    private String externalAuthId;
    @Column(name = "USER_LDAP_DN")
    private String userLdapDn;
    @Column(name = "LAST_UPDATED_STAMP")
    private Date lastUpdatedStamp;
    @Column(name = "LAST_UPDATED_TX_STAMP")
    private Date lastUpdatedTxStamp;
    @Column(name = "CREATED_STAMP")
    private Date createdStamp;
    @Column(name = "CREATED_TX_STAMP")
    private Date createdTxStamp;

    @OneToMany()
    private List<UserLoginPasswordHistory> userLoginPasswordHistories;

    @OneToMany
    private List<UserLoginHistory> userLoginHistories;

    @OneToOne
    private UserLoginSession userLoginSession;

    @ManyToMany
    private Set<SecurityGroup> securityGroups;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getHasLoggedOut() {
        return hasLoggedOut;
    }

    public void setHasLoggedOut(String hasLoggedOut) {
        this.hasLoggedOut = hasLoggedOut;
    }

    public String getLastCurrencyUom() {
        return lastCurrencyUom;
    }

    public void setLastCurrencyUom(String lastCurrencyUom) {
        this.lastCurrencyUom = lastCurrencyUom;
    }

    public String getLastLocale() {
        return lastLocale;
    }

    public void setLastLocale(String lastLocale) {
        this.lastLocale = lastLocale;
    }

    public String getLastTimeZone() {
        return lastTimeZone;
    }

    public void setLastTimeZone(String lastTimeZone) {
        this.lastTimeZone = lastTimeZone;
    }

    public Date getDisabledDateTime() {
        return disabledDateTime;
    }

    public void setDisabledDateTime(Date disabledDateTime) {
        this.disabledDateTime = disabledDateTime;
    }

    public long getSuccessiveFailedLogins() {
        return successiveFailedLogins;
    }

    public void setSuccessiveFailedLogins(long successiveFailedLogins) {
        this.successiveFailedLogins = successiveFailedLogins;
    }

    public String getExternalAuthId() {
        return externalAuthId;
    }

    public void setExternalAuthId(String externalAuthId) {
        this.externalAuthId = externalAuthId;
    }

    public String getUserLdapDn() {
        return userLdapDn;
    }

    public void setUserLdapDn(String userLdapDn) {
        this.userLdapDn = userLdapDn;
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

    public List<UserLoginPasswordHistory> getUserLoginPasswordHistories() {
        return userLoginPasswordHistories;
    }

    public void setUserLoginPasswordHistories(List<UserLoginPasswordHistory> userLoginPasswordHistories) {
        this.userLoginPasswordHistories = userLoginPasswordHistories;
    }

    public List<UserLoginHistory> getUserLoginHistories() {
        return userLoginHistories;
    }

    public void setUserLoginHistories(List<UserLoginHistory> userLoginHistories) {
        this.userLoginHistories = userLoginHistories;
    }

    public UserLoginSession getUserLoginSession() {
        return userLoginSession;
    }

    public void setUserLoginSession(UserLoginSession userLoginSession) {
        this.userLoginSession = userLoginSession;
    }

    public Set<SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(Set<SecurityGroup> securityGroups) {
        this.securityGroups = securityGroups;
    }
}
