package bean;
/**
 * 文章管理表
 */
import java.util.Date;

import annotation.Bean;
import annotation.Column;
import annotation.Id;

@Bean("Entries")
public class Entry {
	@Id("id")
	private Integer id;//文章编号id，使用Integer 达到可扩展
	@Column("title")
	private String title;//文章主题
	@Column("content")
	private String content;
	@Column("comment_hit")
	private Integer commentHit;//评论数
	@Column("allow_comment")
	private Integer allowComment;//是否允许评论
	@Column("status")
	private Integer status;//状态
	@Column("hits")
	private Integer hits;//点击数
	@Column("create_time")
	private Date createTime;//创建时间，即添加时间
	@Column("update_time")
	private Date updateTime;//更新时间
	@Column("category_id")
	private Integer categoryId;//分类id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCommentHit() {
		return commentHit;
	}
	public void setCommentHit(Integer commentHit) {
		this.commentHit = commentHit;
	}
	public Integer getAllowComment() {
		return allowComment;
	}
	public void setAllowComment(Integer allowComment) {
		this.allowComment = allowComment;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	
	
}
