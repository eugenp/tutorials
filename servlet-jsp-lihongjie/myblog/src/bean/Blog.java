package bean;

import annotation.Bean;
import annotation.Column;
import annotation.Id;

/**该类代表数据库中的blog表
 * 博客相关信息表
 * @author Administrator
 *
 */
@Bean("blog")
public class Blog {
	@Id("id")
	private Integer Id;//id
	@Column("name")
	private String name;
	@Column("desription")
	private String desription;
	@Column("entry_number")
	private Integer entryNumber;
	@Column("recent_comment_number")
	private Integer recentCommentNumber;
	@Column("recent_entry_number")
	private Integer recentEntryNumber;
	@Column("backend_page_size")
	private Integer backendPageSize;
	@Column("status")
	private Integer status;
	@Column("comment_audit")
	private Integer commentAudit;
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDecription() {
		return desription;
	}
	public void setDecription(String decription) {
		this.desription = decription;
	}
	public String getDesription() {
		return desription;
	}
	public void setDesription(String desription) {
		this.desription = desription;
	}
	public Integer getEntryNumber() {
		return entryNumber;
	}
	public void setEntryNumber(Integer entryNumber) {
		this.entryNumber = entryNumber;
	}
	public Integer getRecentCommentNumber() {
		return recentCommentNumber;
	}
	public void setRecentCommentNumber(Integer recentCommentNumber) {
		this.recentCommentNumber = recentCommentNumber;
	}
	public Integer getRecentEntryNumber() {
		return recentEntryNumber;
	}
	public void setRecentEntryNumber(Integer recentEntryNumber) {
		this.recentEntryNumber = recentEntryNumber;
	}
	public Integer getBackendPageSize() {
		return backendPageSize;
	}
	public void setBackendPageSize(Integer backendPageSize) {
		this.backendPageSize = backendPageSize;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCommentAudit() {
		return commentAudit;
	}
	public void setCommentAudit(Integer commentAudit) {
		this.commentAudit = commentAudit;
	}
	
	
}
