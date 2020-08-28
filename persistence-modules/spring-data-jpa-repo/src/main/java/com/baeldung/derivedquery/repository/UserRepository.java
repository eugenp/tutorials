package com.baeldung.derivedquery.repository;

import com.baeldung.derivedquery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

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

    List<User> findByNameOrBirthDate(String name, ZonedDateTime birthDate);

    List<User> findByNameOrBirthDateAndActive(String name, ZonedDateTime birthDate, Boolean active);

    List<User> findByNameOrderByName(String name);

    List<User> findByNameOrderByNameDesc(String name);

}
