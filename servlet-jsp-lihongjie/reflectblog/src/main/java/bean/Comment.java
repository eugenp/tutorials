package bean;
/**
 * 评论相关信息表
 */
import java.util.Date;

import annotation.Bean;
import annotation.Column;
import annotation.Id;
@Bean("comment")
public class Comment {
	@Id("id")
	private Integer id;
	@Column("author")
	private String author;//评论的作者
	@Column("email")
	private String email;//评论作者的email
	@Column("content")
	private String content;
	@Column("create_time")
	private Date creatTime;//创建的时间
	@Column("ip")
	private String ip;//评论的ip地址
	@Column("status")
	private Integer status;//状态
	@Column("entry_id")
	private Integer entryId;
	
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getEntryId() {
		return entryId;
	}
	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}
	
	
}
