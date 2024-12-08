package com.baeldung.postgres.datetime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface DateTimeValueRepository extends JpaRepository<DateTimeValues, Serializable> {
}