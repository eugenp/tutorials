package com.baeldung.recordswithjpa.embeddable;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.EmbeddableInstantiator;
import org.hibernate.metamodel.spi.ValueAccess;

public class AuthorInstallator implements EmbeddableInstantiator {

    public boolean isInstance(Object object, SessionFactoryImplementor sessionFactory) {
        return object instanceof Author;
    }

    public boolean isSameClass(Object object, SessionFactoryImplementor sessionFactory) {
        return object.getClass().equals(Author.class);
    }

    @Override
    public Object instantiate(final ValueAccess valueAccess, final SessionFactoryImplementor sessionFactoryImplementor) {
        final String firstName = valueAccess.getValue(0, String.class);
        final String secondName = valueAccess.getValue(1, String.class);
        return new Author(firstName, secondName);
    }
}
