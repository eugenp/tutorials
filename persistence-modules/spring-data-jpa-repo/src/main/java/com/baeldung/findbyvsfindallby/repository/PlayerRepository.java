package com.baeldung.spring.data.persistence.findbyvsfindallby.repository;

import com.baeldung.spring.data.persistence.findbyvsfindallby.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByScoreGreaterThan(Integer target);
}
