package wildfly.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.User;

/**
 * Session Bean implementation class UserBean
 */
@Stateless
public class UserBean implements UserBeanRemote, UserBeanLocal {
    @PersistenceContext(unitName = "wildfly-jpa")
    private EntityManager em;

    @Override
    public List<User> getUsers() {
        return em.createNamedQuery("User.findAll")
            .getResultList();
    }
}
