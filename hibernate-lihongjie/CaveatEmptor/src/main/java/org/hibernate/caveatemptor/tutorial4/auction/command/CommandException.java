package org.hibernate.caveatemptor.tutorial4.auction.command;

import javax.ejb.ApplicationException;

/**
 * A checked exception thrown by command execute methods, wrapping the root cause.
 *
 * @author Christian Bauer
 */
@ApplicationException(rollback = true)
public class CommandException
    extends Exception {

    public CommandException() {}

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
