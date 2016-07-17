package org.baeldung.mocks.jmockit;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
@SuppressWarnings("unchecked")
public class ExpectationsTest {

    @Test
    public void testForAny(@Mocked ExpectationsCollaborator mock) throws Exception {
        new Expectations() {
            {
                mock.methodForAny(anyString, anyInt, anyBoolean, (List<String>) any);
            }
        };
        mock.methodForAny("barfooxyz", 0, Boolean.FALSE, new ArrayList<>());
    }
    }
}
