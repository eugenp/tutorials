package jdbc;
// Developed by George Reese for the book:
// Java Best Practices, Volume II: J2EE
// Ported to the digital@jwt code library by George Reese

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Stack;

/**
 * Implements the pooling algorithm for statement pooling under this
 * library. This algorithm is currently simplistic in the extreme.
 * Specifically, all statements must be non-scrollable and non-updatable
 * and they are stored in a hash based on the actual SQL.
 * <br/>
 * @Last modified $Datte$
 * @version $Revision$
 * @author George Reese
 */
public class StatementPool {
    /**
     * The actual pool of statements hased by their SQL.
     */
    private HashMap statements = new HashMap();

    /**
     * Constructs a new statement pool.
     */
    public StatementPool() {
        super();
    }

    /**
     * Checks to see if a pooled statement is available that
     * matches the specified SQL.
     * @param sql the SQL to match
     * @return true if there is a pooled version of the statement
     */
    public boolean contains(String sql) {
        Stack stack;

        if( !statements.containsKey(sql) ) {
            return false;
        }
        stack = (Stack)statements.get(sql);
        return !stack.empty();
    }

    /**
     * Grabs a statement matching the specified SQL from the pool.
     * @param sql the SQL for which a statement is desired
     * @return the pooled statement matching the SQL
     */
    public PreparedStatement pop(String sql) {
        Stack stack = (Stack)statements.get(sql);

        return (PreparedStatement)stack.pop();
    }

    /**
     * Returns the statement with the specified SQL to the pool.
     * @param sql the SQL of the statement
     * @param stmt the statement to return to the pool
     */
    public void push(String sql, PreparedStatement stmt) {
        Stack stack;
        
        if( statements.containsKey(sql) ) {
            stack = (Stack)statements.get(sql);
        }
        else {
            stack = new Stack();
            statements.put(sql, stack);
        }
        stack.push(stmt);
    }
}
