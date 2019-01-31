package com.baeldung.accessmodifiers.another;

import com.baeldung.accessmodifiers.SuperPublic;

/**
 * @author zn.wang
 */
public class AnotherPublic {
    public AnotherPublic() {
        SuperPublic.publicMethod(); // Available everywhere.
    }
}
