package com.baeldung.hibernate.customtypes;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

public class SalaryType implements CompositeUserType, DynamicParameterizedType {

    private String localCurrency;

    @Override
    public String[] getPropertyNames() {
        return new String[]{"amount", "currency"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{LongType.INSTANCE, StringType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {

        Salary salary = (Salary) component;

        switch (property) {
            case 0:
                return salary.getAmount();
            case 1:
                return salary.getCurrency();
        }

        throw new IllegalArgumentException(property +
                " is an invalid property index for class type " +
                component.getClass().getName());

    }


    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {

        Salary salary = (Salary) component;

        switch (property) {
            case 0:
                salary.setAmount((Long) value);
            case 1:
                salary.setCurrency((String) value);
        }

        throw new IllegalArgumentException(property +
                " is an invalid property index for class type " +
                component.getClass().getName());

    }

    @Override
    public Class returnedClass() {
        return Salary.class;
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

        Salary salary = new Salary();
        salary.setAmount(rs.getLong(names[0]));

        if (rs.wasNull())
            return null;

        salary.setCurrency(rs.getString(names[1]));

        return salary;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {


        if (Objects.isNull(value))
            st.setNull(index, Types.BIGINT);
        else {

            Salary salary = (Salary) value;
            st.setLong(index, SalaryCurrencyConvertor.convert(salary.getAmount(),
                    salary.getCurrency(), localCurrency));
            st.setString(index + 1, salary.getCurrency());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {

        if (Objects.isNull(value))
            return null;

        Salary oldSal = (Salary) value;
        Salary newSal = new Salary();

        newSal.setAmount(oldSal.getAmount());
        newSal.setCurrency(oldSal.getCurrency());

        return newSal;
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

    @Override
    public void setParameterValues(Properties parameters) {
        this.localCurrency = parameters.getProperty("currency");
    }
}
