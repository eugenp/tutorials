package com.baeldung.booking.adapter.out.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>{

}
