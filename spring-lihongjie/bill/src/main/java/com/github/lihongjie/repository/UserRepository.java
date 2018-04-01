package com.github.lihongjie.repository;

import com.github.lihongjie.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lihongjie on 7/23/17.
 *
 *
 */
public interface UserRepository extends JpaRepository<User, String> {

    User findOneByLoginId(String loginId); // 用户名登录

    User findOneByEmail(String email);  // 邮箱登录

}
