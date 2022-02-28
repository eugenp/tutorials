
package com.baeldung.portsandadaptors.core.outports;

import com.baeldung.portsandadaptors.core.model.User;
import java.util.List;

/**
 *
 * @author DeependraTewari
 */
public interface UserPersistence {
    
    public User save(User u);
    public List<User> query(String field, String value);
    public User get(Long uuid);
    public User remove(User u);
}
