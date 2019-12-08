package com.baeldung.hexagon;

import java.util.function.Consumer;

import com.baeldung.hexagon.internal.AskBook;
import com.baeldung.hexagon.internal.DisplayRandomBook;
import com.baeldung.hexagon.port.ICommand;
import com.baeldung.hexagon.port.IGetBook;
import com.baeldung.hexagon.port.IOutput;

/**
 * The only point of communication with invocation driver adapters is the 
 * boundary class. It calls the appropriate command handler.
 * 
 */
public class HexagonBoundary implements ICommand {
	private Consumer<AskBook> model;

	public HexagonBoundary(IGetBook bookFetcher, IOutput output) {
		model = new DisplayRandomBook(bookFetcher, output);
	}

	public void reactTo(Object commandObject) {
		 model.accept((AskBook)commandObject);
	}
}