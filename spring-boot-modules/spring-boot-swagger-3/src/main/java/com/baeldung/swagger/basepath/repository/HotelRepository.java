package com.baeldung.swagger.basepath.repository;

import com.baeldung.swagger.basepath.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {}
