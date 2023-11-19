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

public class CustomStringArrayType implements UserType<String[]> {

    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    @Override
    public Class<String[]> returnedClass() {
        return String[].class;
    }

    @Override
    public boolean equals(String[] x, String[] y) {
        if (x instanceof String[] && y instanceof String[]) {
            return Arrays.deepEquals(x, y);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode(String[] x) {
        return Arrays.hashCode(x);
    }

    @Override
    public String[] nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Array array = rs.getArray(position);
        return array != null ? (String[]) array.getArray() : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, String[] value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (st != null) {
            if (value != null) {
                Array array = session.getJdbcConnectionAccess().obtainConnection().createArrayOf("text", value);
                st.setArray(index, array);
            } else {
                st.setNull(index, Types.ARRAY);
            }
        }
    }

    @Override
    public String[] deepCopy(String[] value) {
        return value != null ? Arrays.copyOf(value, value.length) : null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(String[] value) {
        return value;
    }

    @Override
    public String[] assemble(Serializable cached, Object owner) {
        return (String[]) cached;
    }

    @Override
    public String[] replace(String[] detached, String[] managed, Object owner) {
        return detached;
    }

}
