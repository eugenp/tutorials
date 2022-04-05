package com.baeldung;

import java.lang.reflect.Method;

public class Outer {

    public void outerPublic() {
    }

    private void outerPrivate() {
    }

    class Inner {

        public void innerPublic() {
            outerPrivate();
        }

        public void innerPublicReflection(Outer ob) throws Exception {
            Method method = ob.getClass().getDeclaredMethod("outerPrivate");
            method.invoke(ob);
        }
    }
}