package com.baeldung.hibernate.customtypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.spi.ValueAccess;
import org.hibernate.type.CompositeType;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class PhoneNumberType implements UserType<PhoneNumber>, CompositeUserType<PhoneNumber> {

    @Override
    public int getSqlType() {
        return Types.INTEGER;
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
    public PhoneNumber nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        int countryCode = rs.getInt(position);

        if (rs.wasNull())
            return null;

        int cityCode = rs.getInt(position);
        int number = rs.getInt(position);

        return new PhoneNumber(countryCode, cityCode, number);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, PhoneNumber value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (Objects.isNull(value)) {
            st.setNull(index, Types.INTEGER);
            st.setNull(index+1, Types.INTEGER);
            st.setNull(index+2, Types.INTEGER);
        } else {
            st.setInt(index, value.getCountryCode());
            st.setInt(index+1, value.getCityCode());
            st.setInt(index+2, value.getNumber());
        }
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
            throw new IllegalArgumentException(property +
                    " is an invalid property index for class type " +
                    component.getClass().getName());
    }
    }

    @Override
    public PhoneNumber instantiate(ValueAccess values, SessionFactoryImplementor sessionFactory) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<?> embeddable() {
        return PhoneNumber.class;
    }
}
