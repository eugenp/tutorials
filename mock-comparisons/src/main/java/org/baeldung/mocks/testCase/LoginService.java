package org.baeldung.mocks.testCase;

public class LoginService {

    private LoginDao loginDao;

    public boolean login(UserForm userForm) {
        assert null != userForm;

        int loginResults = loginDao.login(userForm);

        switch (loginResults){
            case 1:
                return true;
            default:
                return false;
        }
    }

    // standard setters and getters
}
