package com.baeldung.circularfifoqueue.example;


import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Assert;
import org.junit.Test;

public class CircularFifoQueueTest {

	private CircularFifoQueueExample testQueue = new CircularFifoQueueExample();
	
	private static final int DEFAULT_SIZE = 32;
	
	private static final int FIXED_SIZE = 5;
	
	private static final int COLLECTION_SIZE = 7;
	
	private static final String TEST_COLOR = "Red";
	
	private static final String TEST_COLOR_BY_INDEX = "Blue";

	@Test
	public void whenUsingDefualtConstructor_correctSizeQueue() {
		CircularFifoQueue bits = testQueue.createDefaultQueue();

		Assert.assertEquals(DEFAULT_SIZE, testQueue.checkMaxSize(bits));
	}

	@Test
	public void givenAddElements_whenUsingIntConstructor_correctSizeQueue() {
		CircularFifoQueue colors = testQueue.createFixedSizeQueue();
		testQueue.addElements(colors);

		Assert.assertEquals(FIXED_SIZE, testQueue.checkMaxSize(colors));
	}
	
	@Test
	public void whenUsingCollectionConstructor_correctSizeQueue() {
		CircularFifoQueue daysOfWeek = testQueue.createQueueFromCollection();

		Assert.assertEquals(COLLECTION_SIZE, testQueue.checkMaxSize(daysOfWeek));
	}
	
	@Test
	public void givenAddElements_whenGetElement_correctElement() {
		CircularFifoQueue colors = testQueue.createFixedSizeQueue();
		testQueue.addElements(colors);
		
		Assert.assertEquals(TEST_COLOR_BY_INDEX, testQueue.getElement(colors));
	}
	
	@Test
	public void givenAddElements_whenPollElement_correctElement() {
		CircularFifoQueue colors = testQueue.createFixedSizeQueue();
		testQueue.addElements(colors);
		
		Assert.assertEquals(TEST_COLOR, testQueue.pollElement(colors));
	}
	
	@Test
	public void givenAddElements_whenPeekQueue_correctElement() {
		CircularFifoQueue colors = testQueue.createFixedSizeQueue();
		testQueue.addElements(colors);
		
		Assert.assertEquals(TEST_COLOR, testQueue.peekQueue(colors));
	}
	
	@Test
	public void givenAddElements_whenElementQueue_correctElement() {
		CircularFifoQueue colors = testQueue.createFixedSizeQueue();
		testQueue.addElements(colors);
		
		Assert.assertEquals(TEST_COLOR, testQueue.elementQueue(colors));
	}
	
	@Test
	public void givenAddElements_whenRemoveElement_correctElement() {
		CircularFifoQueue colors = testQueue.createFixedSizeQueue();
		testQueue.addElements(colors);
		
		Assert.assertEquals(TEST_COLOR, testQueue.removeElement(colors));
	}
	
	@Test
	public void givenFullQueue_whenClearQueue_getIsEmpty() {
		CircularFifoQueue colors = testQueue.createFixedSizeQueue();
		testQueue.addElements(colors);
		testQueue.clearQueue(colors);
		
		Assert.assertEquals(true, testQueue.checkIsEmpty(colors));
	}
	
	@Test
	public void givenFullQueue_whenCheckFull_getIsFull() {
		CircularFifoQueue colors = testQueue.createFixedSizeQueue();
		testQueue.addElements(colors);
		
		Assert.assertEquals(false, testQueue.checkIsFull(colors));
	}
	
	@Test
	public void givenFullQueue_whenAddMoreElements_getIsAtFullCapacity() {
		CircularFifoQueue colors = testQueue.createFixedSizeQueue();
		testQueue.addElements(colors);
		testQueue.addMoreElements(colors);
		
		Assert.assertEquals(true, testQueue.checkisAtFullCapacity(colors));
	}


}
