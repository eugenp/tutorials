package com.baeldung.derivedquery.repository;

import com.baeldung.derivedquery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByName(String name);

    List<User> findByNameIs(String name);

    List<User> findByNameEquals(String name);

    List<User> findByNameIsNull();

    List<User> findByNameNot(String name);

    List<User> findByNameIsNot(String name);

    List<User> findByNameStartingWith(String name);

    List<User> findByNameEndingWith(String name);

    List<User> findByNameContaining(String name);

    List<User> findByNameLike(String name);

    List<User> findByAgeLessThan(Integer age);

    List<User> findByAgeLessThanEqual(Integer age);

    List<User> findByAgeGreaterThan(Integer age);

    List<User> findByAgeGreaterThanEqual(Integer age);

    List<User> findByAgeBetween(Integer startAge, Integer endAge);

    List<User> findByBirthDateAfter(ZonedDateTime birthDate);

    List<User> findByBirthDateBefore(ZonedDateTime birthDate);

    List<User> findByActiveTrue();

    List<User> findByActiveFalse();

    List<User> findByAgeIn(Collection<Integer> ages);

    @Query(value = "select * from users where (name = :nameParam OR birth_date <> :birthDateParam)", nativeQuery = true)
    List<User> findByNameOrBirthDate(@Param(value = "nameParam") String nameParam, @Param(value = "birthDateParam") ZonedDateTime birthDateParam);

    @Query(value = "select * from users where (name = :nameParam OR birth_date <> :birthDateParam) and active = :activeParam", nativeQuery = true)
    List<User> findByNameOrBirthDateAndActive(@Param(value = "nameParam") String nameParam, @Param(value = "birthDateParam") ZonedDateTime birthDateParam,
                                              @Param(value = "activeParam") Boolean activeParam);

    List<User> findByNameOrderByName(String name);

    List<User> findByNameOrderByNameDesc(String name);
    
    List<User> findByNameIsNotNull();
    
    List<User> findByNameOrderByNameAsc(String name);

}
