package com.baeldung.hexagonalarchitecture.notification.port.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<ReaderEntity, Long> {
}
