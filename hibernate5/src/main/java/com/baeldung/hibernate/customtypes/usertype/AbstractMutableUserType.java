package com.baeldung.hibernate.customtypes.usertype;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

abstract class AbstractMutableUserType<T> implements UserType, DynamicParameterizedType {

    protected Class<T> klass;

    @Override
    public void setParameterValues(Properties parameters) {
        klass = ((ParameterType) parameters.get(PARAMETER_TYPE)).getReturnedClass();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        return get(rs, names, session, owner);
    }


    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws SQLException {
        set(st, klass.cast(value), index, session);
    }

    protected abstract T get(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws SQLException;

    protected abstract void set(PreparedStatement st, T value, int index, SharedSessionContractImplementor session)
            throws SQLException;


    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Class<?> returnedClass() {
        return klass;
    }

    @Override
    public boolean equals(Object x, Object y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) {
        return x.hashCode();
    }

    @Override
    public Serializable disassemble(Object o) {
        return (Serializable) deepCopy(o);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object o, Object target, Object owner) {
        return deepCopy(o);
    }
}