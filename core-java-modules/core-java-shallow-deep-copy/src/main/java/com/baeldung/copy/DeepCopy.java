package com.baeldung.copy;

import java.util.*;

public class DeepCopy {
    int intMember;
    ArrayList<Integer> listMember;

    public DeepCopy() {
        intMember = 1;
        listMember = new ArrayList<Integer>();
        listMember.add(1);
    }

    public DeepCopy(DeepCopy sourceObj) {
        this.intMember = sourceObj.intMember;

        this.listMember = new ArrayList();
        for(Integer x : sourceObj.listMember) {
            this.listMember.add(new Integer(x));
        }
        // Alternatively, this can be done by
        // this.listMember.addAll(sourceObj.listMember)
        // as Integer type is immutable.
    }
}