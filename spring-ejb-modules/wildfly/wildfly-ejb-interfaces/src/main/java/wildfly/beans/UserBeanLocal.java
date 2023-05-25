package wildfly.beans;

import java.util.List;

import javax.ejb.Local;

import model.User;

@Local
public interface UserBeanLocal {

    List<User> getUsers();
}
