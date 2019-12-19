package com.baeldung.testCase;

public class LoginService {

    private LoginDao loginDao;

    private String currentUser;

    public boolean login(UserForm userForm) {
        assert null != userForm;

        int loginResults = loginDao.login(userForm);

        switch (loginResults) {
            case 1:
                return true;
            default:
                return false;
        }
    }

    public void setCurrentUser(String username) {
        if (null != username) {
            this.currentUser = username;
        }
    }

    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    // standard setters and getters
}
