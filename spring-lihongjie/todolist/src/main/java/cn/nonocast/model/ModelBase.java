package cn.nonocast.model;

import cn.nonocast.misc.PrePersistUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class ModelBase implements Comparable<ModelBase>, Serializable {
    public interface JsonViewBase {}
	public interface API extends JsonViewBase {}

	@JsonView(API.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

	@JsonView(API.class)
    @Column(nullable = false)
    private Date createdAt;

	@JsonView(API.class)
    @Column(nullable = false)
    private Date updatedAt;

    @PrePersist
    public void prePersist(){
        PrePersistUtil.pre(this);
        createdAt = updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = new Date();
    }

    @Override
    public int compareTo(ModelBase o) {
        return this.getId().compareTo(o.getId());
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass())
            return false;

        return this.getId().equals(((ModelBase) other).getId());
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
