package bean;

import annotation.Bean;
import annotation.Column;
import annotation.Id;

/**
 * 链接表
 * @author 李洪杰
 *
 */
@Bean("Links")
public class Link {
	@Id("id")
	private Integer id; //id
	@Column("name")
	private String name;//添加链接的名称
	@Column("url")
	private String url;//链接的URL
	@Column("display_order")
	private Integer displayOrder;//显示顺序
	@Column("blog_id")
	private Integer blogId;//外键
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Integer getBlogId() {
		return blogId;
	}
	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}
	
	
}
