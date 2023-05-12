package com.baeldung.hibernate.customtypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.spi.ValueAccess;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

public class SalaryType implements UserType<Salary>, CompositeUserType<Salary>, DynamicParameterizedType {

    private String localCurrency;

    @Override
    public Object getPropertyValue(Salary component, int property) throws HibernateException {

        switch (property) {
            case 0:
                return component.getAmount();
            case 1:
                return component.getCurrency();
            default:
                throw new IllegalArgumentException(property +
                        " is an invalid property index for class type " +
                        component.getClass().getName());
        }
    }

    @Override
    public Salary instantiate(ValueAccess values, SessionFactoryImplementor sessionFactory) {
        return null;
    }

    @Override
    public Class<?> embeddable() {
        return Salary.class;
    }

    @Override
    public int getSqlType() {
        return Types.BIGINT;
    }

    @Override
    public Class<Salary> returnedClass() {
        return Salary.class;
    }

    @Override
    public boolean equals(Salary x, Salary y) {
        if (x == y)
            return true;

        if (Objects.isNull(x) || Objects.isNull(y))
            return false;

        return x.equals(y);
    }

    @Override
    public int hashCode(Salary x) {
        return x.hashCode();
    }

    @Override
    public Salary nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Salary salary = new Salary();
        salary.setAmount(rs.getLong(position));

        if (rs.wasNull())
            return null;

        salary.setCurrency(rs.getString(position));

        return salary;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Salary value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (Objects.isNull(value))
            st.setNull(index, Types.BIGINT);
        else {

            st.setLong(index, SalaryCurrencyConvertor.convert(
                    value.getAmount(),
                    value.getCurrency(), localCurrency));
            st.setString(index + 1, value.getCurrency());
        }
    }

    @Override
    public Salary deepCopy(Salary value) {
        if (Objects.isNull(value))
            return null;

        Salary newSal = new Salary();

        newSal.setAmount(value.getAmount());
        newSal.setCurrency(value.getCurrency());

        return newSal;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Salary value) {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Salary assemble(Serializable cached, Object owner) {
        return deepCopy((Salary) cached);
    }

    @Override
    public Salary replace(Salary detached, Salary managed, Object owner) {
        return detached;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        this.localCurrency = parameters.getProperty("currency");
    }
}
