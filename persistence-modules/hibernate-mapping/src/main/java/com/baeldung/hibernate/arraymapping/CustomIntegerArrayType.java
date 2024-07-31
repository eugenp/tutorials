package com.baeldung.hibernate.arraymapping;

import java.io.Serializable;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class CustomIntegerArrayType implements UserType<Integer[]> {

    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    @Override
    public Class<Integer[]> returnedClass() {
        return Integer[].class;
    }

    @Override
    public boolean equals(Integer[] x, Integer[] y) {
        if (x instanceof Integer[] && y instanceof Integer[]) {
            return Arrays.deepEquals(x, y);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode(Integer[] x) {
        return Arrays.hashCode(x);
    }

    @Override
    public Integer[] nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Array array = rs.getArray(position);
        return array != null ? (Integer[]) array.getArray() : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Integer[] value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (st != null) {
            if (value != null) {
                Array array = session.getJdbcConnectionAccess().obtainConnection().createArrayOf("int", value);
                st.setArray(index, array);
            } else {
                st.setNull(index, Types.ARRAY);
            }
        }
    }

    @Override
    public Integer[] deepCopy(Integer[] value) {
        return value != null ? Arrays.copyOf(value, value.length) : null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Integer[] value) {
        return value;
    }

    @Override
    public Integer[] assemble(Serializable cached, Object owner) {
        return (Integer[]) cached;
    }

    @Override
    public Integer[] replace(Integer[] detached, Integer[] managed, Object owner) {
        return detached;
    }

}
