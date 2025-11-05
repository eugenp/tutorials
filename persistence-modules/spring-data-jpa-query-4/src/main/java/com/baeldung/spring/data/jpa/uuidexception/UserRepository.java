package com.baeldung.spring.data.jpa.uuidexception;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<UserWithConverter> findByUuid(UUID uuid);

    @Query("SELECT u FROM User u WHERE u.uuid = CAST(:uuid AS text)")
    Optional<User> findByUuidWithCastFunction(@Param("uuid") UUID uuid);

    @Query(value = "SELECT * FROM User_Tbl WHERE uuid = :uuid\\:\\:text", nativeQuery = true)
    Optional<User> findByUuidWithCastingOperator(@Param("uuid") UUID uuid);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO User_Tbl (uuid, name) VALUES (:uuid\\:\\:text, :name)", nativeQuery = true)
    void insertUser(@Param("uuid") UUID uuid, @Param("name") String name);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM User_TBL", nativeQuery = true)
    void deleteUser();
}
