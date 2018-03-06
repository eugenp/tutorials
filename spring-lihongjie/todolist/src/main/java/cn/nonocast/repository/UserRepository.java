package cn.nonocast.repository;

import cn.nonocast.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT p FROM User p WHERE p.id in (:selected)")
    List<User> findByIds(@Param("selected") List<Long> selected);

    User findByEmail(String email);

    User findByName(String name);

    User findByWechatid(String wechatid);

    Page<User> findByRole(User.Role role, Pageable pageable);

    @Query(value = "SELECT p FROM User p WHERE LOWER(p.email) LIKE LOWER(concat('%',:q,'%')) OR LOWER(p.name) LIKE LOWER(concat('%',:q,'%'))")
    Page<User> findByKeyword(@Param("q") String q, Pageable pageable);

    @Query(value = "SELECT p FROM User p WHERE p.id=:q")
    Page<User> findByKeyword(@Param("q") Long q, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM User p WHERE LOWER(p.email) = LOWER(:email)")
    Boolean existsByEmail(@Param("email") String email);

    @Query("SELECT p FROM User p WHERE LOWER(p.email)=LOWER(:q) or LOWER(name)=LOWER(:q)")
    User findByEmailOrName(@Param("q") String q);
}