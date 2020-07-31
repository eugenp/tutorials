package com.baeldung.exists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author paullatzelsperger
 * @since 2019-03-20
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    boolean existsCarByPower(int power);

    boolean existsCarByModel(String model);

    @Query("select case when count(c)> 0 then true else false end from Car c where c.model = :model")
    boolean existsCarExactCustomQuery(@Param("model") String model);

    @Query("select case when count(c)> 0 then true else false end from Car c where lower(c.model) like lower(:model)")
    boolean existsCarLikeCustomQuery(@Param("model") String model);
}
