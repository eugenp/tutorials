package org.baeldung.JPAAuditDemo.listener;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * Represents listener on entity's events. It contains methods to be called on events like {@link PrePersist},{@link PreUpdate},{@link PreRemove},<br><br>{@link PostPersist},{@link PostUpdate},{@link PostRemove},{@link PostLoad}  
 */
public class LogListener {

    @PrePersist
    public void prePersist(Object entity) {
        System.out.println("prePersist: " + entity);
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        System.out.println("preUpdate: " + entity);
    }

    @PostPersist
    public void postPersist(Object entity) {
        System.out.println("postPersist: " + entity);
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        System.out.println("postUpdate: " + entity);
    }
}
