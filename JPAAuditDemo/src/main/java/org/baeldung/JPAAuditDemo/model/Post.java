package org.baeldung.JPAAuditDemo.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.baeldung.JPAAuditDemo.listener.LogListener;

/**
 * Entity whose lifecycle events are handled by {@link LogListener}. It contains one @PrePersist {@link Post#onSave()} handler.
 */
@Entity
// @EntityListeners({ LogListener.class })
public class Post {

    @Id
    private Integer id;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdDate;

    public Post() {
    }

    public Post(Integer id, String title) {
        super();
        this.id = id;
        this.title = title;
    }

    /**
     * Callback method called before entity to be persisted into database
     */
    @PrePersist
    private void onSave() {
        createdDate = Calendar.getInstance();
    }

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

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Post other = (Post) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        if (createdDate != null)
            return "Post [id=" + id + ", title=" + title + ", createdDate=" + createdDate.getTime() + "]";
        else
            return "Post [id=" + id + ", title=" + title + "]";
    }

}
