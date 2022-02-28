
package com.baeldung.portsandadaptors.core.inports;

import com.baeldung.portsandadaptors.core.model.User;

/**
 *
 * @author DeependraTewari
 */
public interface ManageUser {
    
    public User addNew(User u);
    public User update(User u);
    public User remove(User u);
    
}
