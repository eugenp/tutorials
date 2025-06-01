package com.baeldung.countrows.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.countrows.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Permission findByType(String type);
}

