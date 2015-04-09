package org.baeldung.SpringDataAuditDemo.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * There are three ways to make an entity auditable:
 * <ol>
 *      <li>Extend {@link org.springframework.data.jpa.domain.AbstractAuditable} class. The easiest way because it provides @MappedSuperclass with 4 auditing columns.</li>
 *      <li>Implement {@link org.springframework.data.domain.Auditable} interface. This means that you yourself must include properties for auditing columns.</li>
 *      <li>Use annotations in {@link org.springframework.data.annotation} package. Doesn't enforce you to implement 4 auditing columns but only which you need. Used by this class.</li>
 * </ol> 
 */
@Entity
@Table(name = "orders")
// change table name because 'order' is keyword in mysql
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double price;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastModifiedDate;

    @CreatedBy
    @ManyToOne
    private User createdBy;

    @LastModifiedBy
    @ManyToOne
    private User lastModifiedBy;

    public Order() {
    }

    public Order(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", price=" + price + ", createdDate=" + createdDate.getTime() + ", lastModifiedDate=" + lastModifiedDate.getTime() + "]";
    }

}