package com.baeldung.hibernate.customtypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class AddressType implements CompositeUserType {

    @Override
    public String[] getPropertyNames() {
        return new String[]{"addressLine1", "addressLine2",
                "city", "country", "zipcode"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{StringType.INSTANCE, StringType.INSTANCE,
                StringType.INSTANCE, StringType.INSTANCE, IntegerType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {

        Address empAdd = (Address) component;

        switch (property) {
            case 0:
                return empAdd.getAddressLine1();
            case 1:
                return empAdd.getAddressLine2();
            case 2:
                return empAdd.getCity();
            case 3:
                return empAdd.getCountry();
            case 4:
                return Integer.valueOf(empAdd.getZipCode());
        }

        throw new IllegalArgumentException(property +
                " is an invalid property index for class type " +
                component.getClass().getName());
    }

    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {

        Address empAdd = (Address) component;

        switch (property) {
            case 0:
                empAdd.setAddressLine1((String) value);
            case 1:
                empAdd.setAddressLine2((String) value);
            case 2:
                empAdd.setCity((String) value);
            case 3:
                empAdd.setCountry((String) value);
            case 4:
                empAdd.setZipCode((Integer) value);
        }

        throw new IllegalArgumentException(property +
                " is an invalid property index for class type " +
                component.getClass().getName());

    }

    @Override
    public Class returnedClass() {
        return Address.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;

        if (Objects.isNull(x) || Objects.isNull(y))
            return false;

        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {

        Address empAdd = new Address();
        empAdd.setAddressLine1(rs.getString(names[0]));

        if (rs.wasNull())
            return null;

        empAdd.setAddressLine2(rs.getString(names[1]));
        empAdd.setCity(rs.getString(names[2]));
        empAdd.setCountry(rs.getString(names[3]));
        empAdd.setZipCode(rs.getInt(names[4]));

        return empAdd;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {

        if (Objects.isNull(value))
            st.setNull(index, Types.VARCHAR);
        else {

            Address empAdd = (Address) value;
            st.setString(index, empAdd.getAddressLine1());
            st.setString(index + 1, empAdd.getAddressLine2());
            st.setString(index + 2, empAdd.getCity());
            st.setString(index + 3, empAdd.getCountry());
            st.setInt(index + 4, empAdd.getZipCode());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {

        if (Objects.isNull(value))
            return null;

        Address oldEmpAdd = (Address) value;
        Address newEmpAdd = new Address();

        newEmpAdd.setAddressLine1(oldEmpAdd.getAddressLine1());
        newEmpAdd.setAddressLine2(oldEmpAdd.getAddressLine2());
        newEmpAdd.setCity(oldEmpAdd.getCity());
        newEmpAdd.setCountry(oldEmpAdd.getCountry());
        newEmpAdd.setZipCode(oldEmpAdd.getZipCode());

        return newEmpAdd;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value, SharedSessionContractImplementor session) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return original;
    }
}
