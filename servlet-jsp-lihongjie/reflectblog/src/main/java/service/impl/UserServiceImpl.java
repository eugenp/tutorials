package service.impl;
/*
 * service层，调用DAO层操作数据的方法实现业务逻辑
 */

import bean.User;
import dao.DaoFactory;
import dao.UserDao;
import service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl() {
        userDao = DaoFactory.getInstance().getUserDao();
    }

    public boolean validateLogin(String username, String password) {
        User user = userDao.findByNameAndPwd(username, password);
        if (user != null) {
            return true;
        } else {
            return false;
        }

    }
}
