package org.hibernate.caveatemptor.tutorial4.auction.command;

import auction.dao.DAOFactory;

import javax.ejb.*;


/**
 * The implementation of a generic EJB command handler.
 *<p>
 * This implementation injects a data access object factory into
 * the data access commands. Alternatively, without a DAO layer,
 * you could inject an EntityManager directly into the data access
 * commands, and the CommandHandlerBean would get the EntityManager
 * injected by the EJB container.
 *
 * @author Christian Bauer
 */
@Stateless
public class CommandHandlerBean implements CommandHandler {

    // Use lookup of DAOs with a factory
    DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Command executeCommand(Command command)
            throws CommandException {
        command.execute();
        return command;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Command executeCommand(DataAccessCommand command)
            throws CommandException {
        command.setDAOFactory(daoFactory);
        command.execute();
        return command;
    }
}
