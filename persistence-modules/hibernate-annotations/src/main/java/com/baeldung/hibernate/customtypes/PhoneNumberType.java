package com.baeldung.hibernate.customtypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.ValueAccess;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.util.Objects;

public class PhoneNumberType implements CompositeUserType<PhoneNumber> {

    @Override
    public Object getPropertyValue(PhoneNumber component, int property) throws HibernateException {
        switch (property) {
            case 0:
                return component.getCountryCode();
            case 1:
                return component.getCityCode();
            case 2:
                return component.getNumber();
            default:
                throw new IllegalArgumentException(property + " is an invalid property index for class type " + component.getClass().getName());
        }
    }

    @Override
    public PhoneNumber instantiate(ValueAccess values, SessionFactoryImplementor sessionFactory) {
        return new PhoneNumber(values.getValue(0, Integer.class), values.getValue(1, Integer.class), values.getValue(2,Integer.class));
    }

    @Override
    public Class<?> embeddable() {
        return PhoneNumber.class;
    }

    @Override
    public Class returnedClass() {
        return PhoneNumber.class;
    }

    @Override
    public boolean equals(PhoneNumber x, PhoneNumber y) {
        if (x == y)
            return true;
        if (Objects.isNull(x) || Objects.isNull(y))
            return false;

        return x.equals(y);
    }

    @Override
    public int hashCode(PhoneNumber x) {
        return x.hashCode();
    }

    @Override
    public PhoneNumber deepCopy(PhoneNumber value) {
        if (Objects.isNull(value))
            return null;

        return new PhoneNumber(value.getCountryCode(), value.getCityCode(), value.getNumber());
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(PhoneNumber value) {
        return (Serializable) value;
    }

    @Override
    public PhoneNumber assemble(Serializable cached, Object owner) {
        return (PhoneNumber) cached;
    }

    @Override
    public PhoneNumber replace(PhoneNumber detached, PhoneNumber managed, Object owner) {
        return detached;
    }
}
