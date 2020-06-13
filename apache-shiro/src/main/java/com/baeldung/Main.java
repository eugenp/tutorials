package com.baeldung;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final transient Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Realm realm = new MyCustomRealm();
        SecurityManager securityManager = new DefaultSecurityManager(realm);

        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
          UsernamePasswordToken token 
            = new UsernamePasswordToken("user", "password");
          token.setRememberMe(true);
          try {
              currentUser.login(token);
          } catch (UnknownAccountException uae) {
              log.error("Username Not Found!", uae);
          } catch (IncorrectCredentialsException ice) {
              log.error("Invalid Credentials!", ice);
          } catch (LockedAccountException lae) {
              log.error("Your Account is Locked!", lae);
          } catch (AuthenticationException ae) {
              log.error("Unexpected Error!", ae);
          }
        }

        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        if (currentUser.hasRole("admin")) {
            log.info("Welcome Admin");
        } else if(currentUser.hasRole("editor")) {
            log.info("Welcome, Editor!");
        } else if(currentUser.hasRole("author")) {
            log.info("Welcome, Author");
        } else {
            log.info("Welcome, Guest");
        }

        if(currentUser.isPermitted("articles:compose")) {
            log.info("You can compose an article");
        } else {
            log.info("You are not permitted to compose an article!");
        }

        if(currentUser.isPermitted("articles:save")) {
            log.info("You can save articles");
        } else {
            log.info("You can not save articles");
        }

        if(currentUser.isPermitted("articles:publish")) {
            log.info("You can publish articles");
        } else {
            log.info("You can not publish articles");
        }

        Session session = currentUser.getSession();
        session.setAttribute("key", "value");
        String value = (String) session.getAttribute("key");
        if (value.equals("value")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }

        currentUser.logout();

        System.exit(0);
    }

}
