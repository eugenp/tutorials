package com.baeldung.hibernate.customtypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;


public class PhoneNumberType implements UserType {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER};
    }

    @Override
    public Class returnedClass() {
        return PhoneNumber.class;
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
        int countryCode = rs.getInt(names[0]);

        if (rs.wasNull())
            return null;

        int cityCode = rs.getInt(names[1]);
        int number = rs.getInt(names[2]);
        PhoneNumber employeeNumber = new PhoneNumber(countryCode, cityCode, number);

        return employeeNumber;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {

        if (Objects.isNull(value)) {
            st.setNull(index, Types.INTEGER);
            st.setNull(index+1, Types.INTEGER);
            st.setNull(index+2, Types.INTEGER);
        } else {
            PhoneNumber employeeNumber = (PhoneNumber) value;
            st.setInt(index,employeeNumber.getCountryCode());
            st.setInt(index+1,employeeNumber.getCityCode());
            st.setInt(index+2,employeeNumber.getNumber());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (Objects.isNull(value))
            return null;

        PhoneNumber empNumber = (PhoneNumber) value;
        PhoneNumber newEmpNumber = new PhoneNumber(empNumber.getCountryCode(),empNumber.getCityCode(),empNumber.getNumber());

        return newEmpNumber;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
