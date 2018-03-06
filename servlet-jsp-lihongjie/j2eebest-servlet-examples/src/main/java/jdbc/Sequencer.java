/* $Id$ */
/* Copyright © 2002 George Reese, Imaginet */
package org.dasein.persist;

// Developed by George Reese for the book:
// Java Best Practices, Volume II: J2EE
// Ported to the digital@jwt code library by George Reese

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * A tool for the automatic generation of unique numbers. This class
 * goes to the database once every <code>MAX_KEYS</code> requests to
 * get a new seed for the numbers it generates. This class is thread-safe,
 * meaning multiple threads can be safely requesting unique numbers from it.
 * It is also multi-process safe. In other words, multiple machines can
 * simultaneously be generating unique values and those values will
 * be guaranteed to be unique across all applications. The only caveat
 * is that they all must be using the same algorithm for generating
 * the numbers and getting seeds from the same database. In order to
 * access the database, this class expects a system property called
 * <code>org.dasein.persist.SequencerDSN</code>. It should be set to the
 * name of the DSN that provides connections to the database with the
 * <code>Sequencer</code> table. That table should have the
 * following <code>CREATE</code>:
 * <span class="code">
 * CREATE TABLE Sequencer (
 *     name        VARCHAR(20)     NOT NULL,
 *     seed        BIGINT UNSIGNED NOT NULL,
 *     lastUpdate  BIGINT UNSIGNED NOT NULL,
 *     PRIMARY KEY ( name, lastUpdate )
 * );
 * </span>
 * <br/>
 * Last modified $Date$
 * @version $Revision$
 * @author George Reese
 */
public class Sequencer {
    /**
     * The maximum number of keys that may be safely generated without
     * going to the database. You should lower this number for client
     * applications and other short-lived programs. The number can be
     * higher for applications with long uptimes. All applications
     * using the same sequencer, however, should have the same value
     * for <code>MAX_KEYS</code>.
     */
    static private final long    MAX_KEYS   = 1000000L;
    /**
     * All sequencers currently in memory.
     */
    static private final HashMap sequencers = new HashMap();

    /**
     * Looks to see if a sequencer has been generated for the sequence
     * with the specified name. If not, it will instantiate one.
     * Multiple calls to this method with the same name are guaranteed
     * to receive the same sequencer object. For best performance,
     * classes should save a reference to the sequencer once they get it
     * in order to avoid the overhead of a <code>HashMap</code> lookup.
     * @param name the name of the desired sequencer
     * @return the sequencer with the specified name
     */
    static public final Sequencer getInstance(String name) {
        synchronized( sequencers ) {
            if( !sequencers.containsKey(name) ) {
                Sequencer seq = new Sequencer(name);

                sequencers.put(name, seq);
                return seq;
            }
            else {
                return (Sequencer)sequencers.get(name);
            }
        }
    }

    /**
     * The name of this sequencer.
     */
    private String name     = null;
    /**
     * The seed this sequencer will use for generating its ID's.
     */
    private long   seed     = -1L;
    /**
     * The current sequence within this sequencer's seed.
     */
    private long   sequence = 0L;

    /**
     * Constructs a new sequencer with the specified name.
     * @param nom the name of the sequencer
     */
    private Sequencer(String nom) {
        super();
        name = nom;
    }
    
   /**
     * The SQL for creating a new sequence in the database.
     */
    static private final String CREATE_SEQ =
        "INSERT INTO Sequencer ( name, seed, lastUpdate ) " +
        "VALUES ( ?, ?, ? )";
    /**
     * Constant for the name parameter.
     */
    static private final int INS_NAME   = 1;
    /**
     * Constant for the seed parameter.
     */
    static private final int INS_SEED   = 2; 
    /**
     * Constant for the lastUpdate parameter
     */
    static private final int INS_UPDATE = 3;
    
    /**
     * Creates a new entry in the database for this sequence. This method
     * will throw an error if two threads are simultaneously trying
     * to create a sequence. This state should never occur if you
     * go ahead and create the sequence in the database before
     * deploying the application. It could be avoided by checking
     * SQL exceptions for the proper XOPEN SQLState for duplicate
     * keys. Unfortunately, that approach is error prone due to the lack
     * of consistency in proper XOPEN SQLState reporting in JDBC drivers.
     * @param conn the JDBC connection to use
     * @throws java.sql.SQLException a database error occurred
     */
    private void create(Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(CREATE_SEQ);
            stmt.setString(INS_NAME, name);
            stmt.setLong(INS_SEED, 0L);
            stmt.setLong(INS_UPDATE, System.currentTimeMillis());
            if( stmt.executeUpdate() != 1 ) {
                throw new SQLException("No row was inserted.");
            }
            seed = 0L;
        }
        finally {
            if( rs != null ) {
                try { rs.close(); }
                catch( SQLException e ) { }
            }
            if( stmt != null ) {
                try { stmt.close(); }
                catch( SQLException e ) { }
            }
        }
    }

    /**
     * The name of a DSN to use if none is configured in the system
     * properties.
     */
    static private final String DEFAULT_DSN = "jdbc/sequencer";
    /**
     * The name of the system property to check for a DSN.
     */
    static private final String DSN_PROP    = "org.dasein.persist.DSN";

    /**
     * Generates a new unique number. The unique number is based on the
     * following algorithm:<br/>
     * <i>unique number</i> = <i>seed</i> multiple by
     * <i>maximum keys per seed</i> added to <i>seed sequence</i>
     * <br/>
     * The method then increments the seed sequence for the next
     * ID to be generated. If the ID to be generated would exhaust
     * the seed, then a new seed is retrieved from the database.
     * @return a unique number
     * @throws org.dasein.persist.PersistenceException a data store error
     * occurred while generating the number
     */
    public synchronized long next() throws PersistenceException {
        Connection conn = null;

        // when seed is -1 or the keys for this seed are exhausted,
        // get a new seed from the database
        if( (seed == -1L) || ((sequence + 1) >= MAX_KEYS) ) {
            try {
                String dsn = System.getProperty(DSN_PROP, DEFAULT_DSN);
                InitialContext ctx = new InitialContext();
                DataSource ds = (DataSource)ctx.lookup(dsn);
                
                conn = ds.getConnection();
                reseed(conn);
            }
            catch( SQLException e ) {
                throw new PersistenceException(e);
            }
            catch( NamingException e ) {
                throw new PersistenceException(e);
            }
            finally {
                if( conn != null ) {
                    try { conn.close(); }
                    catch( SQLException e ) { }
                }
            }
        }
        // up the sequence value for the next key
        sequence++;
        // the next key for this sequencer
        return ((seed * MAX_KEYS) + sequence);
    }

    /**
     * The SQL for getting a seed for a sequence from the database.
     */
    static private final String FIND_SEQ =
        "SELECT seed, lastUpdate " +
        "FROM Sequencer " +
        "WHERE name = ?";
    /**
     * Constant for the name parameter.
     */
    static private final int SEL_NAME   = 1;
    /**
     * Constant for the seed column.
     */
    static private final int SEL_SEED   = 1;
    /**
     * Constant for the lastUpdate column.
     */
    static private final int SEL_UPDATE = 2;
    /**
     * The SQL for incrementing the seed in the database.
     */
    static private String UPDATE_SEQ =
        "UPDATE Sequencer " +
        "SET seed = ?, " +
        "lastUpdate = ? " +
        "WHERE name = ? AND lastUpdate = ?";
    /**
     * Constant for the seed parameter.
     */
    static private final int UPD_SEED         = 1;
    /**
     * Constant for the lastUpdate set parameter
     */
    static private final int UPD_SET_UPDATE   = 2;
    /**
     * Constant for the name parameter.
     */
    static private final int UPD_NAME         = 3;
    /**
     * Constant for the lastUpdate parameter.
     */
    static private final int UPD_WHERE_UPDATE = 4;

    /**
     * Gets the next seed from the database for this sequence.
     * @param conn the database connection
     * @throws java.sql.SQLException a database error occurred
     */
    private void reseed(Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Keep in this loop as long as we encounter concurrency errors
            do {
                stmt = conn.prepareStatement(FIND_SEQ);
                stmt.setString(SEL_NAME, name);
                rs = stmt.executeQuery();
                if( !rs.next() ) {
                    // no such sequence, create it
                    {
                        // close resources
                        try { rs.close(); }
                        catch( SQLException e ) { }
                        rs = null;
                        try { stmt.close(); }
                        catch( SQLException e ) { }
                        stmt = null;
                    }
                    create(conn);
                }
                else {
                    long ts;

                    seed = rs.getLong(SEL_SEED) + 1L;
                    ts = rs.getLong(SEL_UPDATE);
                    {
                        // close resources
                        try { rs.close(); }
                        catch( SQLException e ) { }
                        rs = null;
                        try { stmt.close(); }
                        catch( SQLException e ) { }
                        stmt = null;
                    }
                    // increment the seed in the database
                    stmt = conn.prepareStatement(UPDATE_SEQ);
                    stmt.setLong(UPD_SEED, seed);
                    stmt.setLong(UPD_SET_UPDATE, System.currentTimeMillis());
                    stmt.setString(UPD_NAME, name);
                    stmt.setLong(UPD_WHERE_UPDATE, ts);
                    if( stmt.executeUpdate() != 1 ) {
                        // someone changed the database! try again!
                        seed = -1L;
                    }
                }
            } while( seed == -1L );
            sequence = -1L;
        }
        finally {
            if( rs != null ) {
                try { rs.close(); }
                catch( SQLException e ) { }
            }
            if( stmt != null ) {
                try { stmt.close(); }
                catch( SQLException e ) { }
            }
        }   
    }
}
