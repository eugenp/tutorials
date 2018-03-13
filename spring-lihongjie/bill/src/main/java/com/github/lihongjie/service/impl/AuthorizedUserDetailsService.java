package com.github.lihongjie.service.impl;

import com.github.lihongjie.domain.User;
import com.github.lihongjie.domain.UserLoginHistory;
import com.github.lihongjie.domain.support.AuthorizedUser;
import com.github.lihongjie.repository.UserLoginHistoryRepository;
import com.github.lihongjie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by lihongjie on 7/23/17.
 *
 *
 */
public class AuthorizedUserDetailsService extends SavedRequestAwareAuthenticationSuccessHandler implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginHistoryRepository userLoginHistoryRepository;

    /**
     * 登录认证
     * 1.
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user;
        if (username.contains("@")) {
            user = userRepository.findOneByLoginId(username);
        } else {
            user = userRepository.findOneByEmail(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User ID not existing. [" + username + "]");
        }

        return new AuthorizedUser(user);
    }

    /**
     * 登录成功后的操作
     * 1. 更新最后登录时间 TODO
     * 2. 新增登录历史记录
     * 3.
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {


        AuthorizedUser authorizedUser = (AuthorizedUser) authentication.getPrincipal();
        UserLoginHistory userLoginHistory = new UserLoginHistory();
        userLoginHistory.setUserLoginId(authorizedUser.getId());
        userLoginHistory.setFromDate(new Date());
        userLoginHistory.setPasswordUsed(authorizedUser.getCurrentPassword());
        userLoginHistory.setSuccessfulLogin("Y");
        userLoginHistoryRepository.save(userLoginHistory);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
