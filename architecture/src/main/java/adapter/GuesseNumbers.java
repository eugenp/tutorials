package adapter;

import java.util.Scanner;
import core.IGuesseNumbers;

/*
 * Adapter, Concrete implementation of ports
 */
public class GuesseNumbers implements IGuesseNumbers {
	public int currentGuess() {
		Scanner keyboard = new Scanner(System.in);
		return keyboard.nextInt();
	}
}
