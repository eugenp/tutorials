package org.disco.racer.hsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import static org.junit.Assert.assertNotNull;

public class RacersDatabaseShutdownTest {

    static{
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Test
	public void testShutdown() throws Exception{
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = this.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate("SHUTDOWN");
		}finally{
			try{
				stmt.close();
				conn.commit();
				conn.close();
			}catch(Exception e){}
		}
	}


	/**
	 * @return Connection
	 * @throws Exception
	 */
	private Connection getConnection() throws Exception{
		Connection conn =
			DriverManager.getConnection("jdbc:hsqldb:hsql://127.0.0.1", "sa", "");
		assertNotNull("conn was null- is the database running?", conn );
		return conn;
	}

    public static void main(String[] args) {
        JUnitCore.runClasses(RacersDatabaseShutdownTest.class);
    }
}
