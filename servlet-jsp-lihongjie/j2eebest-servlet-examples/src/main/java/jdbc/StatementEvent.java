/* $Id$ */
/* Copyright � 2002 George Reese, Imaginet */
package org.dasein.persist;

// Developed by George Reese for the book:
// Java Best Practices, Volume II: J2EE
// Ported to the digital@jwt code library by George Reese
package jdbc;
import java.sql.PreparedStatement;

/**
 * Represents some event happening within a prepared statement.
 * Currently, the only event that ever happens is the statement gets
 * closed by the application.
 * <br/>
 * Last Modified $Date$
 * @version $Revision$
 * @author George Reese
 */
public class StatementEvent extends EventObject {
    /**
     * The SQL for the prepared statement.
     */
    private String            sql       = null;

    /**
     * Constructs a new statement event for the prepared statement
     * with the specified SQL.
     * @param sql the SQL underneath the prepared statement
     * @param stmt the statement itself
     */
    public StatementEvent(String sql, PreparedStatement stmt) {
        super(stmt);
        this.sql = sql;
    }

    /**
     * @return the SQL for the prepared statement
     */
    public String getSQL() {
        return sql;
    }

    /**
     * @return the source of the event cast to a
     * <code>PreparedStatement</code>
     */
    public PreparedStatement getStatement() {
        return (PreparedStatement)getSource();
    }
}
