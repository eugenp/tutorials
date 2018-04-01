package bean;

import annotation.Bean;
import annotation.Column;
import annotation.Id;

/**
 * User表
 * 对应数据库中的每一张表，用于在层与层之间进行数据的传递
 * @author Administrator
 *
 */
@Bean("Users")
public class User {
	
	@Id("id")
	private Integer id; //id
	@Column("username")
	private String username;//用户名
	@Column("password")
	private String password;//密码
	@Column("email")
	private String email;//email
	@Column("blog_id")
	private Integer blogId;//博客id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getBlogId() {
		return blogId;
	}
	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}
	
}
