package com.baeldung.hibernate.customtypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.ValueAccess;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.util.Objects;

public class AddressType implements CompositeUserType<Address> {

    @Override
    public Object getPropertyValue(Address component, int property) throws HibernateException {

        switch (property) {
            case 0:
                return component.getAddressLine1();
            case 1:
                return component.getAddressLine2();
            case 2:
                return component.getCity();
            case 3:
                return component.getCountry();
            case 4:
                return component.getZipCode();
            default:
                throw new IllegalArgumentException(property +
                        " is an invalid property index for class type " +
                        component.getClass().getName());
        }
    }

    @Override
    public Address instantiate(ValueAccess values, SessionFactoryImplementor sessionFactory) {
        return new Address(values.getValue(0, String.class), values.getValue(1,String.class), values.getValue(2, String.class),
                values.getValue(3, String.class), values.getValue(4,Integer.class));
    }

    @Override
    public Class<?> embeddable() {
        return Address.class;
    }

    @Override
    public Class<Address> returnedClass() {
        return Address.class;
    }

    @Override
    public boolean equals(Address x, Address y) {
        if (x == y)
            return true;

        if (Objects.isNull(x) || Objects.isNull(y))
            return false;

        return x.equals(y);
    }

    @Override
    public int hashCode(Address x) {
        return x.hashCode();
    }

    @Override
    public Address deepCopy(Address value) {
        if (Objects.isNull(value))
            return null;

        Address newEmpAdd = new Address();

        newEmpAdd.setAddressLine1(value.getAddressLine1());
        newEmpAdd.setAddressLine2(value.getAddressLine2());
        newEmpAdd.setCity(value.getCity());
        newEmpAdd.setCountry(value.getCountry());
        newEmpAdd.setZipCode(value.getZipCode());

        return newEmpAdd;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Address value) {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Address assemble(Serializable cached, Object owner) {
        return deepCopy((Address) cached);
    }

    @Override
    public Address replace(Address detached, Address managed, Object owner) {
        return detached;
    }

    @Override
    public boolean isInstance(Object object, SessionFactoryImplementor sessionFactory) {
        return CompositeUserType.super.isInstance(object, sessionFactory);
    }

    @Override
    public boolean isSameClass(Object object, SessionFactoryImplementor sessionFactory) {
        return CompositeUserType.super.isSameClass(object, sessionFactory);
    }
}
