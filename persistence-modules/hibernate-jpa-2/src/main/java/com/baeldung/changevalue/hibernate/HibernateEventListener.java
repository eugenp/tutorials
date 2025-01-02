package com.baeldung.changevalue.hibernate;

import com.baeldung.changevalue.entity.StudentWithHibernateEvent;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

public class HibernateEventListener implements PreInsertEventListener, PreUpdateEventListener {

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        upperCaseStudentName(event.getEntity());
        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        upperCaseStudentName(event.getEntity());
        return false;
    }

    private void upperCaseStudentName(Object entity) {
        if (entity instanceof StudentWithHibernateEvent) {
            StudentWithHibernateEvent student = (StudentWithHibernateEvent) entity;
            student.setName(StringUtils.upperCase(student.getName()));
        }
    }

}
