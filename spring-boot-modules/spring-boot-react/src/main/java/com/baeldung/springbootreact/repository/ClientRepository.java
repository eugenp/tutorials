package com.baeldung.springbootreact.repository;

import com.baeldung.springbootreact.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
