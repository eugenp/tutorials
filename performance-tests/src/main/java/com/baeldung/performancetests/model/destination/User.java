package com.baeldung.performancetests.model.destination;

import com.google.common.base.Objects;
import com.googlecode.jmapper.annotations.JGlobalMap;
import com.googlecode.jmapper.annotations.JMapConversion;

@JGlobalMap
public class User {
    private String username;
    private String email;
    private AccountStatus userAccountStatus;

    public User(String username, String email, AccountStatus userAccountStatus) {
        this.username = username;
        this.email = email;
        this.userAccountStatus = userAccountStatus;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountStatus getUserAccountStatus() {
        return userAccountStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() == com.baeldung.performancetests.model.source.User.class) {
            com.baeldung.performancetests.model.source.User user =
              (com.baeldung.performancetests.model.source.User) o;
            return Objects.equal(username, user.getUsername()) &&
                    Objects.equal(email, user.getEmail()) &&
                    userAccountStatus.ordinal() == user.getUserAccountStatus().ordinal();
        }
        if (o.getClass() != getClass()) return false;
        User user = (User) o;
        return Objects.equal(username, user.username) &&
          Objects.equal(email, user.email) &&
          userAccountStatus == user.userAccountStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, email, userAccountStatus);
    }

    public void setUserAccountStatus(AccountStatus userAccountStatus) {
        this.userAccountStatus = userAccountStatus;
    }


    @JMapConversion(from = "userAccountStatus", to = "userAccountStatus")
    public AccountStatus conversion(com.baeldung.performancetests.model.source.AccountStatus status) {
        AccountStatus accountStatus = null;
        switch(status) {
            case ACTIVE:
                accountStatus = AccountStatus.ACTIVE;
                break;
            case NOT_ACTIVE:
                accountStatus = AccountStatus.NOT_ACTIVE;
                break;

            case BANNED:
                accountStatus = AccountStatus.BANNED;
                break;
        }
        return accountStatus;
    }
}
