package bean;

import annotation.Bean;
import annotation.Column;
import annotation.Id;

/**
 * 类别表
 *
 * @author Administrator
 */
@Bean("Category")
public class Category {
    @Id("id")
    private Integer id;//类别id，即编号
    @Column("name")
    private String name;//名字
    @Column("display_order")
    private Integer displayOrder;//显示顺序
    @Column("description")
    private String description;//描述
    @Column("blog_id")
    private Integer blogId;//博客id

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

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }


}
