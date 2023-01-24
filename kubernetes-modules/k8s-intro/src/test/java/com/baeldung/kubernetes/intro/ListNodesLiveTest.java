package com.baeldung.kubernetes.intro;

import org.junit.jupiter.api.Test;

class ListNodesLiveTest {
    @Test
    void whenListNodes_thenSuccess()  throws Exception {
        ListNodes.main(new String[] {});
    }
}
