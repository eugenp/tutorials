package baeldung.data;

import baeldung.model.User;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by adam.
 */
@Repository
public abstract class UserRepository extends AbstractEntityRepository<User, Long> {

    public List<User> findByFirstName(String firstName) {
        return typedQuery("select u from User u where u.firstName = ?1")
          .setParameter(1, firstName)
          .getResultList();
    }

    public abstract List<User> findByLastName(String lastName);

    @Query("select u from User u where u.firstName = ?1")
    public abstract Collection<User> findUsersWithFirstName(String firstName);

    @Query(value = "select * from User where firstName = ?1", isNative = true)
    public abstract Collection<User> findUsersWithFirstNameNative(String firstName);

}
