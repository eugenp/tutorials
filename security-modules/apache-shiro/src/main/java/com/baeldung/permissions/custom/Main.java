package com.baeldung.permissions.custom;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final transient Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        IniRealm realm = new IniRealm();
        Ini ini = Ini.fromResourcePath(Main.class.getResource("/com/baeldung/shiro/permissions/custom/shiro.ini").getPath());
        realm.setIni(ini);
        realm.setPermissionResolver(new PathPermissionResolver());
        realm.init();
        SecurityManager securityManager = new DefaultSecurityManager(realm);

        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
          UsernamePasswordToken token = new UsernamePasswordToken("paul.reader", "password4");
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

        if(currentUser.isPermitted("/articles/drafts/new-article")) {
            log.info("You can access articles");
        } else {
            log.info("You cannot access articles!");
        }
        currentUser.logout();
    }

}
