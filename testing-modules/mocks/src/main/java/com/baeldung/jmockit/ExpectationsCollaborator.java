package com.baeldung.jmockit;

import java.util.List;

public interface ExpectationsCollaborator {
    String methodForAny1(String s, int i, Boolean b);
    void methodForAny2(Long l,  List<String> lst);
    String methodForWith1(String s, int i);
    void methodForWith2(Boolean b, List<String> l);
    String methodForNulls1(String s, List<String> l);
    void methodForNulls2(String s, List<String> l);
    void methodForTimes1();
    void methodForTimes2();
    void methodForTimes3();
    void methodForArgThat(Object o);
    String methodReturnsString();
    int methodReturnsInt();
    int methodForDelegate(int i);
}
