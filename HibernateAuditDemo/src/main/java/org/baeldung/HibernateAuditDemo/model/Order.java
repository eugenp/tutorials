package org.baeldung.HibernateAuditDemo.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

@Entity
// change table name because 'order' is keyword in mysql
@Table(name = "orders")
// this tell Envers to audit (track changes to) this entity
@Audited
public class Order {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    // this tells hibernate envers to audit modifications to this property. It's tested in EnversTest.testFindRevisionAtWhichPropertyChanged
    @Audited(withModifiedFlag = true)
    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdDate;

    @OneToOne
    private Customer customer;

    public Order() {
    }

    public Order(Double price) {
        this.price = price;
    }

    public Order(Double price, Calendar createdDate) {
        super();
        this.price = price;
        this.createdDate = createdDate;
    }

    public Order(Double price, Calendar createdDate, Customer customer) {
        super();
        this.price = price;
        this.createdDate = createdDate;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        return "Order [id=" + id + ", price=" + price + ", createdDate=" + createdDate.getTime() + "]";
    }

}