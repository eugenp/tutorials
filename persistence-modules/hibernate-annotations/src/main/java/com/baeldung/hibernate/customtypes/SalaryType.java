package com.baeldung.hibernate.customtypes;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

public class SalaryType implements UserType<Salary>, DynamicParameterizedType {

    private String localCurrency;

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
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

        String salaryValue = rs.getString(position);

        salary.setAmount(Long.parseLong(salaryValue.split(" ")[1]));

        salary.setCurrency(salaryValue.split(" ")[0]);

        return salary;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Salary value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (Objects.isNull(value))
            st.setNull(index, Types.VARCHAR);
        else {
            Long salaryValue = SalaryCurrencyConvertor.convert(value.getAmount(),
                    value.getCurrency(), localCurrency);
            st.setString(index, value.getCurrency() + " " + salaryValue);
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
        return deepCopy(value);
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
