package org.baeldung.mocks.jmockit;

import java.util.List;

public interface ExpectationsCollaborator {
    String methodForAny1(String s, int i, Boolean b);
    void methodForAny2(Long l,  List<String> lst);
    void methodForWith(String s, int i, Boolean b, List<String> l);
    void methodForNulls(String s, List<String> l, List<Integer> m);
    void methodForTimes1();
    void methodForTimes2();
    void methodForTimes3();
    void methodForArgThat(Object o);
    String methodReturnsString();
    int methodReturnsInt();
    Object methodForDelegate(int i);
}
