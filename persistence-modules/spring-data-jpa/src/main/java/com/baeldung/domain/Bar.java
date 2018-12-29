package com.baeldung.domain;

import com.google.common.collect.Sets;
import org.hibernate.annotations.OrderBy;
import org.hibernate.envers.Audited;
import org.jboss.logging.Logger;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@NamedQuery(name = "Bar.findAll", query = "SELECT b FROM Bar b")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Bar implements Serializable {

    private static Logger logger = Logger.getLogger(Bar.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "bar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(clause = "NAME DESC")
    // @NotAudited
    private Set<Foo> fooSet = Sets.newHashSet();
    @Column(name = "operation")
    private String operation;
    @Column(name = "timestamp")
    private long timestamp;
    @Column(name = "created_date", updatable = false, nullable = false)
    @CreatedDate
    private long createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    private long modifiedDate;
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    public Bar() {
        super();
    }

    public Bar(final String name) {
        super();

        this.name = name;
    }

    public Set<Foo> getFooSet() {
        return fooSet;
    }

    // API

    public void setFooSet(final Set<Foo> fooSet) {
        this.fooSet = fooSet;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public OPERATION getOperation() {
        return OPERATION.parse(operation);
    }

    public void setOperation(final String operation) {
        this.operation = operation;
    }

    public void setOperation(final OPERATION operation) {
        this.operation = operation.getValue();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final long createdDate) {
        this.createdDate = createdDate;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Bar other = (Bar) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Bar [name=").append(name).append("]");
        return builder.toString();
    }

    @PrePersist
    public void onPrePersist() {
        logger.info("@PrePersist");
        audit(OPERATION.INSERT);
    }

    @PreUpdate
    public void onPreUpdate() {
        logger.info("@PreUpdate");
        audit(OPERATION.UPDATE);
    }

    @PreRemove
    public void onPreRemove() {
        logger.info("@PreRemove");
        audit(OPERATION.DELETE);
    }

    private void audit(final OPERATION operation) {
        setOperation(operation);
        setTimestamp((new Date()).getTime());
    }

    public enum OPERATION {
        INSERT, UPDATE, DELETE;
        private String value;

        OPERATION() {
            value = toString();
        }

        public static OPERATION parse(final String value) {
            OPERATION operation = null;
            for (final OPERATION op : OPERATION.values()) {
                if (op.getValue().equals(value)) {
                    operation = op;
                    break;
                }
            }
            return operation;
        }

        public String getValue() {
            return value;
        }
    }

}
