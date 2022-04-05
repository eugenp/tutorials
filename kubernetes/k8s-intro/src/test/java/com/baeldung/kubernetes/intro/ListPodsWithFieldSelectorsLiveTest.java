package com.baeldung.kubernetes.intro;

import org.junit.jupiter.api.Test;

class ListPodsWithFieldSelectorsLiveTest {
    @Test
    void givenEqualitySelector_whenListPodsWithFieldSelectors_thenSuccess()  throws Exception {
        ListPodsWithFieldSelectors.main(new String[] {
            "metadata.namespace=ns1"
        });
    }

    @Test
    void givenInequalitySelector_whenListPodsWithFieldSelectors_thenSuccess()  throws Exception {
        ListPodsWithFieldSelectors.main(new String[] {
            "metadata.namespace!=ns1"
        });
    }
    
    @Test
    void givenChainedSelector_whenListPodsWithFieldSelectors_thenSuccess()  throws Exception {
        ListPodsWithFieldSelectors.main(new String[] {
            "metadata.namespace=ns1",
            "status.phase=Running"
        });
    }
}
