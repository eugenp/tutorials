package org.baeldung.mocks.jmockit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Mocked;
import mockit.StrictExpectations;
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

    @Test
    public void testWithTimes(@Mocked ExpectationsCollaborator mock) {
        // more config
        new Expectations() {
            {
                // exactly 2 invocations to foo() are expected
                mock.methodForTimes1();
                times = 2;
                // we expect from 1 to 3 invocations to bar()
                mock.methodForTimes2();
                minTimes = 1;
                maxTimes = 3;
                mock.methodForTimes3(); // "minTimes = 1" is implied
            }
        };
        mock.methodForTimes1();
        mock.methodForTimes1();
        mock.methodForTimes2();
        mock.methodForTimes2();
        mock.methodForTimes2();
        mock.methodForTimes3();
    }

    @Test
    public void testCustomArgumentMatching(@Mocked ExpectationsCollaborator mock) {
        new Expectations() {
            {
                mock.methodForArgThat(withArgThat(new BaseMatcher<Object>() {
                    @Override
                    public boolean matches(Object item) {
                        return item instanceof Model && "info".equals(((Model) item).getInfo());
                    }

                    @Override
                    public void describeTo(Description description) {
                        // NOOP
                    }
                }));
            }
        };
        mock.methodForArgThat(new Model());
    }

    @Test
    public void testResultAndReturns(@Mocked ExpectationsCollaborator mock) {
        new StrictExpectations() {
            {
                // return "foo", an exception and lastly "bar"
                mock.methodReturnsString();
                result = "foo";
                result = new Exception();
                result = "bar";
                // return 1, 2, 3
                mock.methodReturnsInt();
                result = new int[] { 1, 2, 3 };
                // return "foo" and "bar"
                mock.methodReturnsString();
                returns("foo", "bar");
                // return only 1
                mock.methodReturnsInt();
                result = 1;
            }
        };
        assertEquals("Should return foo", "foo", mock.methodReturnsString());
        try {
            mock.methodReturnsString();
        } catch (Exception e) {
            // NOOP
        }
        assertEquals("Should return bar", "bar", mock.methodReturnsString());
        assertEquals("Should return 1", 1, mock.methodReturnsInt());
        assertEquals("Should return 2", 2, mock.methodReturnsInt());
        assertEquals("Should return 3", 3, mock.methodReturnsInt());
        assertEquals("Should return foo", "foo", mock.methodReturnsString());
        assertEquals("Should return bar", "bar", mock.methodReturnsString());
        assertEquals("Should return 1", 1, mock.methodReturnsInt());
    }

    @Test
    public void testDelegate(@Mocked ExpectationsCollaborator mock) {
        new StrictExpectations() {
            {
                // return "foo", an exception and lastly "bar"
                mock.methodForDelegate(anyInt);
                times = 2;
                result = new Delegate() {
                    public int delegate(int i) throws Exception {
                        if (i < 3) {
                            return 5;
                        } else {
                            throw new Exception();
                        }
                    }
                };
            }
        };
        assertEquals("Should return 5", 5, mock.methodForDelegate(1));
        try {
            mock.methodForDelegate(3);
        } catch (Exception e) {
            // NOOP
        }
    }
}
