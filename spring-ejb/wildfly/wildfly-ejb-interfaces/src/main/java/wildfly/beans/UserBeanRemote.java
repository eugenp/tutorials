package wildfly.beans;

import java.util.List;

import javax.ejb.Remote;

import model.User;

@Remote
public interface UserBeanRemote {

    List<User> getUsers();
}
