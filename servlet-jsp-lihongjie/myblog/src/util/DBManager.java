package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接类，
 * @author Administrator
 *
 */
public class DBManager {
	private DBManager() {
		
	}
	//静态块实现 驱动加载，保证只做一次
	static {
			try {
				Class.forName(Constant.DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//获得数据库连接
	public static Connection getConn() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Constant.URL,Constant.USER,Constant.PWD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	
	}
	//关闭数据库连接
	public static void closeConn(Connection conn) {
		if(conn != null){
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}