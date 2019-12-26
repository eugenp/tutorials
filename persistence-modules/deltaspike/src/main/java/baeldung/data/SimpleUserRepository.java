package baeldung.data;

import baeldung.model.User;
import org.apache.deltaspike.data.api.FirstResult;
import org.apache.deltaspike.data.api.MaxResults;
import org.apache.deltaspike.data.api.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by adam.
 */
@Repository(forEntity = User.class)
public abstract class SimpleUserRepository {
    public abstract Collection<User> findAll();

    public abstract Collection<User> findAllOrderByFirstNameAsc(@FirstResult int start, @MaxResults int size);

    public abstract Collection<User> findTop2OrderByFirstNameAsc();

    public abstract Collection<User> findFirst2OrderByFirstNameAsc();

    public abstract List<User> findAllOrderByFirstNameAsc();

    public abstract List<User> findAllOrderByFirstNameAscLastNameDesc();

    public abstract User findById(Long id);

    public abstract Collection<User> findByFirstName(String firstName);

    public abstract User findAnyByLastName(String lastName);

    public abstract Collection<User> findAnyByFirstName(String firstName);

    public abstract Collection<User> findByFirstNameAndLastName(String firstName, String lastName);

    public abstract Collection<User> findByFirstNameOrLastName(String firstName, String lastName);

    public abstract Collection<User> findByAddress_city(String city);

    public abstract int count();

    public abstract void remove(User user);
}
