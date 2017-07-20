package com.baeldung.circularfifoqueue.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class CircularFifoQueueExample {
	
	public CircularFifoQueue<String> createDefaultQueue() {
		CircularFifoQueue<String> bits = new CircularFifoQueue();
		return bits;
	}
	
	public CircularFifoQueue<String> createFixedSizeQueue() {
		CircularFifoQueue<String> colors = new CircularFifoQueue<String>(5);
		return colors;
	}
	
	public CircularFifoQueue<String> createQueueFromCollection() {
		List<String> days = new ArrayList<String>();
		days.add("Monday");
		days.add("Tuesday");
		days.add("Wednesday");
		days.add("Thursday");
		days.add("Friday");
		days.add("Saturday");
		days.add("Sunday");

		CircularFifoQueue<String> daysOfWeek = new CircularFifoQueue<String>(days);
		return daysOfWeek;
	}
	
	public void addElements(CircularFifoQueue<String> colors) {
		colors.add("Red");
		colors.add("Blue");
		colors.add("Green");
		colors.offer("White");
		colors.offer("Black");
	}
	
	public void addMoreElements(CircularFifoQueue<String> colors) {
		colors.add("Orange");
		colors.add("Violet");
		colors.add("Pink");
	}
	
	public String peekQueue(CircularFifoQueue<String> colors) {
		String colorsHead = colors.peek();
		return colorsHead;
	}
	
	public String elementQueue(CircularFifoQueue<String> colors) {
		String colorsHead = colors.element();
		return colorsHead;
	}
	
	public String getElement(CircularFifoQueue<String> colors) {
		String color = colors.get(1);
		return color;
	}
	
	public String pollElement(CircularFifoQueue<String> colors) {
		String color = colors.poll();
		return color;
	}
	
	public String removeElement(CircularFifoQueue<String> colors) {
		String color = colors.remove();
		return color;
	}
	
	public void clearQueue(CircularFifoQueue<String> colors) {
		colors.clear();
	}
	
	public int checkMaxSize(CircularFifoQueue<String> bits) {
		int maxSize = bits.maxSize();
		return maxSize;
	}
	
	public int checkSize(CircularFifoQueue<String> colors) {
		int size = colors.size();
		return size;
	}
	
	public boolean checkIsEmpty(CircularFifoQueue<String> colors) {
		boolean isEmpty = colors.isEmpty();
		return isEmpty;		
	}
	
	public boolean checkIsFull(CircularFifoQueue<String> colors) {
		boolean isFull = colors.isFull();
		return isFull;		
	}
	
	public boolean checkisAtFullCapacity(CircularFifoQueue<String> colors) {
		boolean isFull = colors.isAtFullCapacity();
		return isFull;		
	}							
}
