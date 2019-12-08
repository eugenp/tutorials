package com.baeldung.hexagon.port;

public interface ICommand{
	public void reactTo(Object command);
}
