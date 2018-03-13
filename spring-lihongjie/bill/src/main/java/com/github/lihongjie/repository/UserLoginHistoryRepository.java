package com.github.lihongjie.repository;

import com.github.lihongjie.domain.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lihongjie on 7/23/17.
 */
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, String> {


}
