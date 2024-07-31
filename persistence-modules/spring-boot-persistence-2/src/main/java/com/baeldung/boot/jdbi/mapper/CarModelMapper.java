package com.baeldung.boot.jdbi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

import com.baeldung.boot.jdbi.domain.CarModel;

@Component
public class CarModelMapper implements RowMapper<CarModel>{

    @Override
    public CarModel map(ResultSet rs, StatementContext ctx) throws SQLException {
        return CarModel.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .sku(rs.getString("sku"))
            .yearDate(rs.getInt("year"))
            .build();
    }

}
