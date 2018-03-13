package org.hibernate.caveatemptor.tutorial4.auction.command;


/**
 * A generic handler for EJB commands.
 *
 * @author Christian Bauer
 */
public interface CommandHandler {

	public Command executeCommand(Command command)
		throws CommandException;

    public Command executeCommand(DataAccessCommand command)
        throws CommandException;

}
