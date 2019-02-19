package com.baeldung.jpacriteria.repository;

import com.baeldung.jpacriteria.entity.MobilePhone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobilePhoneJpaRepository extends JpaRepository<MobilePhone, Integer> {
}
