package org.baeldung.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@SuppressWarnings({"rawtypes", "unchecked"})
@RunWith(EasyMockRunner.class) //necessary for @Mock to work with EasyMock
public class MockitoVsEasyMock {

	@Mock private List mockitoList;
	@org.easymock.Mock
	private List easymockList;

	@Before()
	public void setUp() {
		//remove comments to use normal instantiation instead of @Annotations
		//mockitoList = Mockito.mock(List.class);
		MockitoAnnotations.initMocks(this);
		//easymockList = EasyMock.createMock(List.class);
	}

	/* behavior verification */
	@Test
	public void testBehaviorMockito() {
		//we add something to the list
		mockitoList.add("object");
		//we verify if add with object param was called at least once
		Mockito.verify(mockitoList).add("object");
	}

	@Test
	public void testBehaviorEasyMock() {
		//we create the expected bevahior
		EasyMock.expect(easymockList.add("object")).andReturn(true);
		//replay to activate mock
		EasyMock.replay(easymockList);
		easymockList.add("object");
		//we verify if add with object param was called at least once
		EasyMock.verify(easymockList);
	}

	/* verify number of invocations */
	@Test
	public void fixedNoOfInvocationsMockito() {
		/* check if add was called two times with any object argument */
		mockitoList.add("object1");
		mockitoList.add("Object2");
		Mockito.verify(mockitoList, Mockito.times(2)).add(Matchers.anyObject());
	}

	@Test
	public void fixedNoOfInvocationsEasyMock() {
		/* check if add was called two times with any object argument */
		EasyMock.expect(easymockList.add(EasyMock.anyObject())).andReturn(true).times(2);
		EasyMock.replay(easymockList);

		easymockList.add("object");
		easymockList.add("object2");
		EasyMock.verify(easymockList);
	}


	/* stubbing */
	@Test
	public void testStubbingMockito() {
		Mockito.when(mockitoList.get(0)).thenReturn("Custom Object");
		Mockito.when(mockitoList.get(1)).thenThrow(new NullPointerException());

		assertEquals(mockitoList.get(0), "Custom Object");
		try {
			mockitoList.get(1);
			fail("Should have thrown a null pointer exception!");
		} catch (final NullPointerException ex) {
			assertTrue(true);
		}
	}

	@Test
	public void testStubbingEasyMock() {
		EasyMock.expect(easymockList.get(0)).andReturn("Custom Object");
		EasyMock.expect(easymockList.get(1)).andThrow(new NullPointerException());
		EasyMock.replay(easymockList);

		assertEquals(easymockList.get(0), "Custom Object");
		try {
			easymockList.get(1);
			fail("Should have thrown a null pointer exception.");
		} catch (final NullPointerException ex) {
			assertTrue(true);
		}
		EasyMock.verify(easymockList);
	}


	/* Argument matchers */
	@Test
	public void customMatcherMockito() {
		Mockito.when(mockitoList.addAll(Matchers.argThat(new CustomMockitoMatcher()))).thenReturn(true);
		mockitoList.addAll(Arrays.asList("one", "two"));
		Mockito.verify(mockitoList).addAll(Matchers.argThat(new CustomMockitoMatcher()));
	}

	@Test
	public void customMatcherEasyMock() {
		EasyMock.expect(easymockList.addAll(TwoElemList())).andReturn(true);
		EasyMock.replay(easymockList);
		easymockList.addAll(Arrays.asList("unu", "doi"));
		EasyMock.verify(easymockList);
	}

	private static List TwoElemList() {
		EasyMock.reportMatcher(new CustomEasyMockMatcher());
		return null;
	}


	/* Verifying exact number of invocations */
	@Test
	public void noOfInvocationsMockito() {
		mockitoList.add("one");
		mockitoList.add("two");
		mockitoList.add("three");

		Mockito.verify(mockitoList, Mockito.atLeast(3)).add(Matchers.anyString());
		Mockito.verify(mockitoList, Mockito.never()).get(Matchers.anyInt());
	}

	@Test
	public void noOfInvocationsEasyMock() {
		EasyMock.expect(easymockList.add(EasyMock.anyString())).andReturn(true).times(3, Integer.MAX_VALUE);
		EasyMock.replay(easymockList);
		easymockList.add("one");
		easymockList.add("two");
		easymockList.add("three");
		EasyMock.verify(easymockList);
		//by default is method is never expected and called easymock will throw assertone rror
	}


	/* recursive stubbing */
	@Test
	public void consecutiveCallsMockito() {
		Mockito.when(mockitoList.get(Matchers.anyInt())).thenReturn(100).thenReturn(30).thenReturn(15);
		assertEquals(100, mockitoList.get(1));
		assertEquals(30, mockitoList.get(999));
		assertEquals(15, mockitoList.get(1));
		Mockito.verify(mockitoList, Mockito.atLeast(3)).get(Matchers.anyInt());
	}

	@Test
	public void consecutiveCallsEasyMock() {
		EasyMock.expect(easymockList.get(EasyMock.anyInt())).andReturn(100).andReturn(30).andReturn(15);
		EasyMock.replay(easymockList);
		assertEquals(100, easymockList.get(1));
		assertEquals(30, easymockList.get(999));
		assertEquals(15, easymockList.get(1));
		EasyMock.verify(easymockList);
	}


	/* stubbing with callbakz */
	@Test
	public void customStubbingAnswerMockito() {
		Mockito.when(mockitoList.get(Matchers.anyInt())).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(final InvocationOnMock invocation) throws Throwable {
				final Object[] args = invocation.getArguments();
				return "called with arguments: " + args;
			}
		});
		System.out.println(mockitoList.get(5));;
		Mockito.verify(mockitoList).get(5);
	}

	@Test
	public void customStubbingAnswerEasyMock() {
		EasyMock.expect(easymockList.get(EasyMock.anyInt())).andAnswer(new IAnswer<Object>() {
			@Override
			public Object answer() throws Throwable {
				final Object[] args = EasyMock.getCurrentArguments();
				return "called witha rguments: " + args;
			}
		});
		EasyMock.replay(easymockList);
		System.out.println(easymockList.get(15));
		EasyMock.verify(easymockList);
	}


	/* stub void methods */
	@Test
	public void testVoidStubMockito() {
		Mockito.doNothing().when(mockitoList).add(0, "String");
		mockitoList.add(0, "String");
		Mockito.verify(mockitoList).add(0, "String");
	}

	@Test
	public void testVoidStubEasyMock() {
		easymockList.add(0, "String");
		EasyMock.expectLastCall();
		EasyMock.replay(easymockList);
		easymockList.add(0, "String");
		EasyMock.verify(easymockList);
	}


	/* Capturing arguments */
	@Test
	public void captureArgumentsMockito() {
		final ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
		mockitoList.add("String");
		Mockito.verify(mockitoList).add(argument.capture());
		System.out.println("You entered: " + argument.getValue());
	}

	@Test
	public void captureArgumentsEasyMock() {
		final Capture argument = EasyMock.newCapture();
		EasyMock.expect(easymockList.add((EasyMock.capture(argument)))).andReturn(true);
		EasyMock.replay(easymockList);
		easymockList.add("Stringx");
		assertEquals("Stringx", argument.getValue());
	}

	@Test
	public void realPartialMocksMockito() {
		ArrayList realList = Mockito.mock(ArrayList.class);
		Mockito.when(realList.get(Matchers.anyInt())).thenCallRealMethod();
	}

	@Test
	public void realPartialMocksEasyMock() {
		easymockList = EasyMock.partialMockBuilder(ArrayList.class).addMockedMethod("add", Object.class).createMock();
	}
}
