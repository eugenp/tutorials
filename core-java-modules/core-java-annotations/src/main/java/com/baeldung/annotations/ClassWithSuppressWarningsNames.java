package com.baeldung.annotations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"serial", "unchecked"})
public class ClassWithSuppressWarningsNames implements Serializable {

//    private static final long serialVersionUID = -1166032307853492833L;

    @SuppressWarnings("unused")
    public static void suppressBoxingWarning() {
        int value = 5;
        int unusedVar = 10; // no warning here
        List<Integer> list = new ArrayList<>();
        list.add(Integer.valueOf(value));
    }

    @SuppressWarnings("deprecation")
    void suppressDeprecatedWarning() {
        ClassWithSuppressWarningsNames cls = new ClassWithSuppressWarningsNames();
        cls.deprecatedMethod(); // no warning here
    }

    @SuppressWarnings("fallthrough")
    String suppressFallthroughWarning() {
        int day = 5;
        switch (day) {
            case 5:
                return "This is day 5";
//            break; // no warning here
            case 10:
                return "This is day 10";
//            break;
            default:
                return "This default day";
        }
    }

    @Deprecated
    String deprecatedMethod() {
        return "deprecated method";
    }

}
