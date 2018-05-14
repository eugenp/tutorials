package com.baeldung.accessmodifiers.another;

import com.baeldung.accessmodifiers.SuperPublic;

public class AnotherPublic {
    public void AnotherPublic() {
        SuperPublic.publicMethod(); // Available everywhere
    }

}
