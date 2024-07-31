package com.baeldung.kubernetes.intro;

import org.junit.jupiter.api.Test;

class ListPodsPagedLiveTest {
    @Test
    void whenListPodsPage_thenSuccess()  throws Exception {
        ListPodsPaged.main(new String[] {});
    }
}
