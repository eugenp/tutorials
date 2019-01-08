package adapter;

import core.IDisplayNumber;

/*
 * Adapter, Concrete implementation of ports
 */
public class DisplayResult implements IDisplayNumber {

	public void showNumber(String text) {
		System.out.print(text);
	}
}