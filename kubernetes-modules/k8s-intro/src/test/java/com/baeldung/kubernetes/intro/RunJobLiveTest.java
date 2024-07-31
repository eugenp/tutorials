package com.baeldung.kubernetes.intro;

import org.junit.jupiter.api.Test;

class RunJobLiveTest {
    @Test
    void whenWatchPods_thenSuccess()  throws Exception {
        RunJob.main(new String[] {});
    }
}
