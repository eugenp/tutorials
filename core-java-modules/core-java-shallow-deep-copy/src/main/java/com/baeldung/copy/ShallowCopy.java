package com.baeldung.copy;
import java.util.*;

public class ShallowCopy {
    int intMember;
    ArrayList<Integer> listMember;

    public ShallowCopy() {
        intMember = 1;
        listMember = new ArrayList<Integer>();
        listMember.add(1);
    }

    public ShallowCopy(ShallowCopy sourceObj) {
        this.intMember = sourceObj.intMember;
        this.listMember = sourceObj.listMember;
    }
}