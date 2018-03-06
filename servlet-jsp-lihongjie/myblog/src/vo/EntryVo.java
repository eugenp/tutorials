package vo;

import java.util.Date;

import bean.Category;

/**
 * java对象：PO,VO,DAO,BO,POJO
 * PO:persistant object:持久化对象 ，与数据库中的表相映射的Java对象，属性对应表中的字段
 * VO:value object:值对象,用于业务层之间的数据传递，和PO一样仅仅包含数据，当持久化对象不能满足我们需要的时候，比如list对象中存了分类id，但没有分类的name
 * 					但是是抽象出来的业务对象，在与表对应的同时，还可以包含业务的需求。
 * POJO：plain old java object ：简单java 对象
 * BO：Business object：营业对象，从营业模型的角度，
 * DAO：Data Access Object：数据连接对象，负责持久层的操作。
 * @author Administrator
 *
 */
public class EntryVo {
	
	private Integer id;//文章编号id，使用Integer 达到可扩展

	private String title;//文章主题
	
	private String content;
	
	private Integer commentHit;//评论数
	
	private Integer allowComment;//是否允许评论
	
	private Integer status;//状态
	
	private Integer hits;//点击数
	
	private Date createTime;//创建时间，即添加时间
	
	private Date updateTime;//更新时间
	
	private Integer categoryId;//分类id
	//类别
	private Category category;//对象

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
