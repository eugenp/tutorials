package jdbc;

// Developed by George Reese for the book:
// Java Best Practices, Volume II: J2EE
// Ported to the digital@jwt code library by George Reese

/**
 * Implemented by statement poolers to enable a statement to
 * return to the pool when it has been closed. You can therefore
 * write your own pooling mechanism without writing your own logical
 * prepared statement object simply by implementing this interface.
 * <br/>
 * Last modified $Date$
 * @version $Revision$
 * @author George Reese
 */
public interface StatementEventListener {
    /**
     * Statements call this method to let the listener know that
     * an application has closed the statement and the pooling
     * mechanism is now free to return the statement to the pool.
     * @param evt the event containing information about the closure
     */
    void statementClosed(StatementEvent evt);
}
