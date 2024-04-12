package com.baeldung.kubernetes.intro;

import org.junit.jupiter.api.Test;

class ListNodesAsyncLiveTest {
    @Test
    void whenListNodes_thenSuccess()  throws Exception {
        ListNodesAsync.main(new String[] {});
    }
}
