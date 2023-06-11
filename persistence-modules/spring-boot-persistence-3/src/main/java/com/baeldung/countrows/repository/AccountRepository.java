package com.baeldung.countrows.repository;

import com.baeldung.countrows.entity.Account;
import com.baeldung.countrows.entity.Permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    long countByUsername(String username);

    long countByPermission(Permission permission);

    long countByPermissionAndCreatedOnGreaterThan(Permission permission, Timestamp ts);
}
