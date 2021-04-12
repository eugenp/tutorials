package com.baeldung.kubernetes.intro;

import org.junit.jupiter.api.Test;

class WatchNodesLiveTest {
    @Test
    void whenWatchNodes_thenSuccess()  throws Exception {
        WatchNodes.main(new String[] {});
    }
}
