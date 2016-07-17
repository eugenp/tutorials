package org.baeldung.mocks.jmockit;

import java.util.ArrayList;
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

    @Test
    public void testForWith(@Mocked ExpectationsCollaborator mock) throws Exception {
        new Expectations() {
            {
                mock.methodForWith(withSubstring("foo"), withNotEqual(1), withNotNull(), withInstanceOf(List.class));
            }
        };
        mock.methodForWith("barfooxyz", 2, Boolean.TRUE, new ArrayList<>());
    }

    @Test
    public void testWithNulls(@Mocked ExpectationsCollaborator mock) {
        // more config
        new Expectations() {
            {
                mock.methodForNulls(anyString, null, (List<Integer>) withNull());
            }
        };
        mock.methodForNulls("blablabla", new ArrayList<String>(), null);
    }
    }
}
