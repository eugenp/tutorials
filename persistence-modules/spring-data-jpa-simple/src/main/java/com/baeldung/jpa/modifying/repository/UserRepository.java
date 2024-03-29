package com.baeldung.jpa.modifying.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.baeldung.jpa.modifying.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Stream<User> findAllByName(String name);

    void deleteAllByCreationDateAfter(LocalDate date);

    @Query("select u from User u where u.email like '%@gmail.com'")
    List<User> findUsersWithGmailAddress();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u set u.active = false where u.lastLoginDate < :date")
    void deactivateUsersNotLoggedInSince(@Param("date") LocalDate date);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete User u where u.active = false")
    int deleteDeactivatedUsers();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "alter table USERS add column deleted int not null default 0", nativeQuery = true)
    void addDeletedColumn();

    @Query("delete User u where u.active = false")
    int deleteDeactivatedUsersWithNoModifyingAnnotation();
}
