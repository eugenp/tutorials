package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.model.Car;
import com.baeldung.hexagonal.architecture.model.Category;
import com.baeldung.hexagonal.architecture.model.Mark;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Car(
                rs.getLong("id"),
                Mark.valueOf(rs.getString("mark")),
                Category.valueOf(rs.getString("category")),
                rs.getBigDecimal("price"),
                rs.getInt("construction_year")
        );
    }
}
