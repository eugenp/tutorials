package org.baeldung.mocks.jmockit;

import java.util.List;

public interface ExpectationsCollaborator {
    void methodForAny(String s, int i, Boolean b, List<String> l);
    void methodForWith(String s, int i, Boolean b, List<String> l);
}
