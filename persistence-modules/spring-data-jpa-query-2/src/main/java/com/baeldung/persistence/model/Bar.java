package com.baeldung.persistence.model;

import com.google.common.collect.Sets;
import org.hibernate.annotations.OrderBy;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

@Entity
@NamedQuery(name = "Bar.findAll", query = "SELECT b FROM Bar b")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Bar implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(Bar.class.toString());

    public enum OPERATION {
        INSERT, UPDATE, DELETE;
        private final String value;

        OPERATION() {
            value = toString();
        }

        public String getValue() {
            return value;
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
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "bar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(clause = "name DESC")
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

    // API

    public Set<Foo> getFooSet() {
        return fooSet;
    }

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

    public void setOperation(final String operation) {
        this.operation = operation;
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
            return other.name == null;
        } else
            return name.equals(other.name);
    }

    @Override
    public String toString() {
        return "Bar [name=" + name + "]";
    }

    @PrePersist
    public void onPrePersist() {
        LOGGER.info("@PrePersist");
        audit(OPERATION.INSERT);
    }

    @PreUpdate
    public void onPreUpdate() {
        LOGGER.info("@PreUpdate");
        audit(OPERATION.UPDATE);
    }

    @PreRemove
    public void onPreRemove() {
        LOGGER.info("@PreRemove");
        audit(OPERATION.DELETE);
    }

    private void audit(final OPERATION operation) {
        setOperation(operation);
        setTimestamp((new Date()).getTime());
    }

}
