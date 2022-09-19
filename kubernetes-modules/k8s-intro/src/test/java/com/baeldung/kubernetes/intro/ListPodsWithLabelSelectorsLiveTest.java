package com.baeldung.kubernetes.intro;

import org.junit.jupiter.api.Test;

class ListPodsWithLabelSelectorsLiveTest {
    @Test
    void givenEqualitySelector_whenListPodsWithLabelSelectors_thenSuccess()  throws Exception {
        ListPodsWithLabelSelectors.main(new String[] {
          "app=httpd"
        });
    }
    
    @Test
    void givenInqualitySelector_whenListPodsWithLabelSelectors_thenSuccess()  throws Exception {
        ListPodsWithLabelSelectors.main(new String[] {
          "app!=httpd"
        });
    }

    @Test
    void givenInSetSelector_whenListPodsWithLabelSelectors_thenSuccess()  throws Exception {
        ListPodsWithLabelSelectors.main(new String[] {
          "app in (httpd,test)"
        });
    }

    @Test
    void givenNotInSetSelector_whenListPodsWithLabelSelectors_thenSuccess()  throws Exception {
        ListPodsWithLabelSelectors.main(new String[] {
          "app notin (httpd)"
        });
    }

    @Test
    void givenLabelPresentSelector_whenListPodsWithLabelSelectors_thenSuccess()  throws Exception {
        ListPodsWithLabelSelectors.main(new String[] {
          "app"
        });
    }
    
    @Test
    void givenLabelNotPresentSelector_whenListPodsWithLabelSelectors_thenSuccess()  throws Exception {
        ListPodsWithLabelSelectors.main(new String[] {
          "!app"
        });
    }
    
    @Test
    void givenChainedSelector_whenListPodsWithLabelSelectors_thenSuccess()  throws Exception {
        ListPodsWithLabelSelectors.main(new String[] {
          "app=httpd",
          "!foo"
        });
    }
}
