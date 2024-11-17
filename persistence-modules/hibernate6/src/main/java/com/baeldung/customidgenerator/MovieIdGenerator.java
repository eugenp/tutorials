package com.baeldung.customidgenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class MovieIdGenerator extends SequenceStyleGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner) throws HibernateException {
        final Long id;
        if (this.allowAssignedIdentifiers() && owner instanceof Movie) {
            id = ((Movie) owner).getId();
        } else {
            id = null;
        }

        return id != null ? id : super.generate(session, owner);
    }

    @Override
    public boolean allowAssignedIdentifiers() {
        return true;
    }
}
