package com.baeldung.nulls;

public class Preconditions {

    public void goodAccept(String one, String two, String three) {
        if (null == one || null == two || three == null)
            throw new IllegalArgumentException();
    }

    public void badAccept(String one, String two, String three){
        if (null == one)
            throw new IllegalArgumentException();
        else
            process(one);

        if (null == two)
            throw new IllegalArgumentException();
        else
            process(two);

        if (null == three)
            throw new IllegalArgumentException();
        else
            process(three);

    }

    private void process(String one) {
    }


}
