package com.baeldung.examples.copying;

public class PrimitiveCopying implements CopyExample<Integer>{

    @Override
    public Integer copy(Integer source) {
        int target = source;
        return target;
    }

}
