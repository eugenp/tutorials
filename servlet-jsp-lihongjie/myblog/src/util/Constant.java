package util;
/**
 * 常量类，其中有数据库的常量
 * @author Administrator
 *
 */
public class Constant {
	public static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String URL ="jdbc:sqlserver://localhost:1433;DatabaseName=MyBlogDB";
	public static final String USER="sa";
	public static final String PWD="123456";
	
	//博客的id
	public static final Integer BLOG_ID = new Integer(1);
	
	public static final Integer PAGESIZE = new Integer(3);
}
