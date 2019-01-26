package com.baeldung.dao.repositories.user;

import com.baeldung.domain.user.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Integer> , UserRepositoryCustom{

    Stream<User> findAllByName(String name);

    @Query("SELECT u FROM User u WHERE u.status = 1")
    Collection<User> findAllActiveUsers();

    @Query(value = "SELECT * FROM USERS.USERS u WHERE u.status = 1", nativeQuery = true)
    Collection<User> findAllActiveUsersNative();

    @Query("SELECT u FROM User u WHERE u.status = ?1")
    User findUserByStatus(Integer status);

    @Query(value = "SELECT * FROM USERS.Users u WHERE u.status = ?1", nativeQuery = true)
    User findUserByStatusNative(Integer status);

    @Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
    User findUserByStatusAndName(Integer status, String name);

    @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
    User findUserByStatusAndNameNamedParams(@Param("status") Integer status, @Param("name") String name);

    @Query(value = "SELECT * FROM USERS.Users u WHERE u.status = :status AND u.name = :name", nativeQuery = true)
    User findUserByStatusAndNameNamedParamsNative(@Param("status") Integer status, @Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
    User findUserByUserStatusAndUserName(@Param("status") Integer userStatus, @Param("name") String userName);

    @Query("SELECT u FROM User u WHERE u.name like ?1%")
    User findUserByNameLike(String name);

    @Query("SELECT u FROM User u WHERE u.name like :name%")
    User findUserByNameLikeNamedParam(@Param("name") String name);

    @Query(value = "SELECT * FROM USERS.users u WHERE u.name LIKE ?1%", nativeQuery = true)
    User findUserByNameLikeNative(String name);

    @Query(value = "SELECT u FROM User u")
    List<User> findAllUsers(Sort sort);

    @Query(value = "SELECT u FROM User u ORDER BY id")
    Page<User> findAllUsersWithPagination(Pageable pageable);

    @Query(value = "SELECT * FROM USERS.Users ORDER BY id", countQuery = "SELECT count(*) FROM USERS.Users", nativeQuery = true)
    Page<User> findAllUsersWithPaginationNative(Pageable pageable);

    @Modifying
    @Query("update User u set u.status = :status where u.name = :name")
    int updateUserSetStatusForName(@Param("status") Integer status, @Param("name") String name);

    @Modifying
    @Query(value = "UPDATE USERS.Users u SET u.status = ? WHERE u.name = ?", nativeQuery = true)
    int updateUserSetStatusForNameNative(Integer status, String name);
}
