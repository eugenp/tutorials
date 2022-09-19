package com.baeldung.kubernetes.intro;

import org.junit.jupiter.api.Test;

class WatchPodsLiveTest {
    @Test
    void whenWatchPods_thenSuccess()  throws Exception {
        WatchPods.main(new String[] {});
    }
}
