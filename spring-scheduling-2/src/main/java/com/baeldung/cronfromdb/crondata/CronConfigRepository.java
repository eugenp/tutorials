package com.baeldung.cronfromdb.crondata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CronConfigRepository extends JpaRepository<CronEntity, Long> {
}