package com.baeldung.findbyvsfindallby.repository;

import com.baeldung.findbyvsfindallby.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByScoreGreaterThan(Integer target);

    List<Player> findAllByScoreGreaterThan(Integer target);

    Optional<Player> findFirstByScoreGreaterThan(Integer target);
}
