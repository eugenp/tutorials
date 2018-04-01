package org.hibernate.caveatemptor.tutorial4.auction.command;

import java.io.Serializable;

/**
 * The interface for generic commands between presentation and business tier.
 *
 * @author Christian Bauer
 */
public interface Command extends Serializable {
	public void execute() throws CommandException;
}
