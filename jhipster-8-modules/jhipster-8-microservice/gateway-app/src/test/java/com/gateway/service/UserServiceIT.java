package com.gateway.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.gateway.IntegrationTest;
import com.gateway.config.Constants;
import com.gateway.domain.User;
import com.gateway.repository.UserRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.security.RandomUtil;

/**
 * Integration tests for {@link UserService}.
 */
@IntegrationTest
class UserServiceIT {

    private static final String DEFAULT_LOGIN = "johndoe";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";

    private static final String DEFAULT_LASTNAME = "doe";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";

    private static final String DEFAULT_LANGKEY = "dummy";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    public void init() {
        userRepository.deleteAllUserAuthorities().block();
        userRepository.deleteAll().block();
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(true);
        user.setEmail(DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
        user.setCreatedBy(Constants.SYSTEM);
    }

    @Test
    void assertThatUserMustExistToResetPassword() {
        userRepository.save(user).block();
        Optional<User> maybeUser = userService.requestPasswordReset("invalid.login@localhost").blockOptional();
        assertThat(maybeUser).isNotPresent();

        maybeUser = userService.requestPasswordReset(user.getEmail()).blockOptional();
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.orElse(null).getEmail()).isEqualTo(user.getEmail());
        assertThat(maybeUser.orElse(null).getResetDate()).isNotNull();
        assertThat(maybeUser.orElse(null).getResetKey()).isNotNull();
    }

    @Test
    void assertThatOnlyActivatedUserCanRequestPasswordReset() {
        user.setActivated(false);
        userRepository.save(user).block();

        Optional<User> maybeUser = userService.requestPasswordReset(user.getLogin()).blockOptional();
        assertThat(maybeUser).isNotPresent();
        userRepository.delete(user).block();
    }

    @Test
    void assertThatResetKeyMustNotBeOlderThan24Hours() {
        Instant daysAgo = Instant.now().minus(25, ChronoUnit.HOURS);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey(resetKey);
        userRepository.save(user).block();

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey()).blockOptional();
        assertThat(maybeUser).isNotPresent();
        userRepository.delete(user).block();
    }

    @Test
    void assertThatResetKeyMustBeValid() {
        Instant daysAgo = Instant.now().minus(25, ChronoUnit.HOURS);
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey("1234");
        userRepository.save(user).block();

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey()).blockOptional();
        assertThat(maybeUser).isNotPresent();
        userRepository.delete(user).block();
    }

    @Test
    void assertThatUserCanResetPassword() {
        String oldPassword = user.getPassword();
        Instant daysAgo = Instant.now().minus(2, ChronoUnit.HOURS);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey(resetKey);
        userRepository.save(user).block();

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey()).blockOptional();
        assertThat(maybeUser).isPresent();
        assertThat(maybeUser.orElse(null).getResetDate()).isNull();
        assertThat(maybeUser.orElse(null).getResetKey()).isNull();
        assertThat(maybeUser.orElse(null).getPassword()).isNotEqualTo(oldPassword);

        userRepository.delete(user).block();
    }

    @Test
    void assertThatNotActivatedUsersWithNotNullActivationKeyCreatedBefore3DaysAreDeleted() {
        Instant now = Instant.now();
        user.setActivated(false);
        user.setActivationKey(RandomStringUtils.random(20));
        User dbUser = userRepository.save(user).block();
        dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        userRepository.save(user).block();
        LocalDateTime threeDaysAgo = LocalDateTime.ofInstant(now.minus(3, ChronoUnit.DAYS), ZoneOffset.UTC);
        List<User> users = userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo)
            .collectList()
            .block();
        assertThat(users).isNotEmpty();
        userService.removeNotActivatedUsers();
        users = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo).collectList().block();
        assertThat(users).isEmpty();
    }

    @Test
    void assertThatNotActivatedUsersWithNullActivationKeyCreatedBefore3DaysAreNotDeleted() {
        Instant now = Instant.now();
        user.setActivated(false);
        User dbUser = userRepository.save(user).block();
        dbUser.setCreatedDate(now.minus(4, ChronoUnit.DAYS));
        userRepository.save(user).block();
        LocalDateTime threeDaysAgo = LocalDateTime.ofInstant(now.minus(3, ChronoUnit.DAYS), ZoneOffset.UTC);
        List<User> users = userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(threeDaysAgo)
            .collectList()
            .block();
        assertThat(users).isEmpty();
        userService.removeNotActivatedUsers();
        Optional<User> maybeDbUser = userRepository.findById(dbUser.getId()).blockOptional();
        assertThat(maybeDbUser).contains(dbUser);
    }
}
