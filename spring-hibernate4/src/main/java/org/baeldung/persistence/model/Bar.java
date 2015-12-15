package org.baeldung.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.OrderBy;
import org.hibernate.envers.Audited;
import org.jboss.logging.Logger;

import com.google.common.collect.Sets;

@Entity
@NamedQuery(name = "Bar.findAll", query = "SELECT b FROM Bar b")
@Audited
public class Bar implements Serializable {

    private static Logger logger = Logger.getLogger(Bar.class);

    public enum OPERATION {
        INSERT, UPDATE, DELETE;
        private String value;

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
    @OrderBy(clause = "NAME DESC")
    // @NotAudited
    private Set<Foo> fooSet = Sets.newHashSet();

    @Column(name = "operation")
    private String operation;

    @Column(name = "timestamp")
    private long timestamp;

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

}
