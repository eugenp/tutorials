package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;
import bean.User;
import dao.UserDao;
import exception.DaoException;
/**
 *数据访问层的实现类：JDBC  Hibernate
 * @author Administrator
 *
 */
public class UserDaoImpl extends GenerateDaoImpl<User> implements UserDao {
	private Connection conn = null;
	User user = null;

	public User findByNameAndPwd(String username, String password) {
		
		Connection conn = null;
		
		try {
			conn = DBManager.getConn();
			String sql = "select * from Users where username='"+username+"'and password='"+password+"'";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
			}
		}catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			DBManager.closeConn(conn);
		}
		return user;
	}
	
	public void save(User user) {
		conn = DBManager.getConn();
		String sql = "insert into Users (username,password,email,blog_id) values(?,?,?,?)";
					//或者写成：insert into Users (username,password,email,blog_id) values ('yuer','lihongjie','1234@qq.com',3)
		String username = user.getUsername();
		String pwd = user.getPassword();
		String email = user.getEmail();
		Integer blogId = user.getBlogId();
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, pwd);
			ps.setString(3, email);
			ps.setInt(4,blogId);
			ps.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new DaoException();
		}finally {
			DBManager.closeConn(conn);
		}
	
		
	}

	public void delete(User user) {
		
	}

	public void update(User user) {
		
	}

	public List<User> findAll() {
		List<User> list=new ArrayList<User>();
		conn=DBManager.getConn();
		User user=null;
		try {
			PreparedStatement ps=conn.prepareStatement("select * from Users");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				user=new User();
				user.setBlogId(rs.getInt("blog_id"));
				user.setEmail(rs.getString("email"));
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				user.setUsername(rs.getString("username"));
			}
			list.add(user);
		} catch (SQLException e) {
			throw new DaoException();
		}finally {
			DBManager.closeConn(conn);
		}
		return list;
	}

	public User findById(Integer id) {
		return null;
	}

	public User findById(Object id) {
		return null;
	}

}
