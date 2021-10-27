package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.model.Car;
import com.baeldung.hexagonal.architecture.model.Category;
import com.baeldung.hexagonal.architecture.model.Mark;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class CarRepositoryImpl extends AbstractRepository implements CarRepository {

    public List<Car> findBy(Mark mark, Category category, BigDecimal price, Integer constructionYear) {
        String sql = "select * from car " +
                        "where mark = :mark " +
                        "and category = :category " +
                        "and price = :price " +
                        "and construction_year = :construction_year";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("mark", mark.name());
        mapSqlParameterSource.addValue("category", category.name());
        mapSqlParameterSource.addValue("price", price);
        mapSqlParameterSource.addValue("construction_year", constructionYear);

        return namedParameterJdbcTemplate.query(
                sql,
                mapSqlParameterSource,
                (rs, rowNum) ->
                        new Car(
                            rs.getLong("id"),
                            Mark.valueOf(rs.getString("mark")),
                            Category.valueOf(rs.getString("category")),
                            rs.getBigDecimal("price"),
                            rs.getInt("construction_year")
        ));
    }
}
