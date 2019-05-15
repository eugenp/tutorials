package com.baeldung.multipledb.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.multipledb.model.user.PossessionMultipleDB;

public interface PossessionRepository extends JpaRepository<PossessionMultipleDB, Long> {

}
