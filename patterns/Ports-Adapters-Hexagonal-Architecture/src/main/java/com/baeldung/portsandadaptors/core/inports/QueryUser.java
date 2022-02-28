
package com.baeldung.portsandadaptors.core.inports;

import com.baeldung.portsandadaptors.core.model.User;
import java.util.List;
import java.util.UUID;

/**
 *
 * @ DeependraTewari
 */
public interface QueryUser {
    
    public List<User> findByName(String name);
    public User findByID(Long uuid);
    public List<User> findByTwitterHandle(String handle);
    
}
