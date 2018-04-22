package com.baeldung.hibernate.customtypes.usertype;

import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntegerListToStringUserType extends AbstractMutableUserType<List<Integer>> {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Object deepCopy(Object value) {
        if (value == null) {
            return null;
        }
        return new ArrayList<>((List<Integer>) value);
    }


    @Override
    protected List<Integer> get(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws SQLException {
        String value = rs.getString(names[0]);

        if (value == null) {
            return null;
        }
        return Arrays.stream(value.substring(1, value.length() - 1).split(","))
                .map(Integer::valueOf).collect(Collectors.toList());
    }

    @Override
    protected void set(PreparedStatement st, List<Integer> value, int index, SharedSessionContractImplementor session)
            throws SQLException {

        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            st.setString(index, "{" + value.stream().map(String::valueOf).collect(Collectors.joining(",")) + "}");
        }
    }
}