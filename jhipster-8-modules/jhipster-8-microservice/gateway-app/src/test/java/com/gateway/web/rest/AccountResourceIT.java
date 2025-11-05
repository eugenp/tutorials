package com.gateway.web.rest;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.IntegrationTest;
import com.gateway.config.Constants;
import com.gateway.domain.User;
import com.gateway.repository.AuthorityRepository;
import com.gateway.repository.UserRepository;
import com.gateway.security.AuthoritiesConstants;
import com.gateway.service.UserService;
import com.gateway.service.dto.AdminUserDTO;
import com.gateway.service.dto.PasswordChangeDTO;
import com.gateway.web.rest.vm.KeyAndPasswordVM;
import com.gateway.web.rest.vm.ManagedUserVM;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link AccountResource} REST controller.
 */
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_TIMEOUT)
@IntegrationTest
class AccountResourceIT {

    static final String TEST_USER_LOGIN = "test";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebTestClient accountWebTestClient;

    @Test
    @WithUnauthenticatedMockUser
    void testNonAuthenticatedUser() {
        accountWebTestClient
            .get()
            .uri("/api/authenticate")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .isEmpty();
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN)
    void testAuthenticatedUser() {
        accountWebTestClient
            .get()
            .uri("/api/authenticate")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo(TEST_USER_LOGIN);
    }

    @Test
    @WithMockUser(TEST_USER_LOGIN)
    void testGetExistingAccount() {
        Set<String> authorities = new HashSet<>();
        authorities.add(AuthoritiesConstants.ADMIN);

        AdminUserDTO user = new AdminUserDTO();
        user.setLogin(TEST_USER_LOGIN);
        user.setFirstName("john");
        user.setLastName("doe");
        user.setEmail("john.doe@jhipster.com");
        user.setImageUrl("http://placehold.it/50x50");
        user.setLangKey("en");
        user.setAuthorities(authorities);
        userService.createUser(user).block();

        accountWebTestClient
            .get()
            .uri("/api/account")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody()
            .jsonPath("$.login")
            .isEqualTo(TEST_USER_LOGIN)
            .jsonPath("$.firstName")
            .isEqualTo("john")
            .jsonPath("$.lastName")
            .isEqualTo("doe")
            .jsonPath("$.email")
            .isEqualTo("john.doe@jhipster.com")
            .jsonPath("$.imageUrl")
            .isEqualTo("http://placehold.it/50x50")
            .jsonPath("$.langKey")
            .isEqualTo("en")
            .jsonPath("$.authorities")
            .isEqualTo(AuthoritiesConstants.ADMIN);
    }

    @Test
    void testGetUnknownAccount() {
        accountWebTestClient
            .get()
            .uri("/api/account")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void testRegisterValid() throws Exception {
        ManagedUserVM validUser = new ManagedUserVM();
        validUser.setLogin("test-register-valid");
        validUser.setPassword("password");
        validUser.setFirstName("Alice");
        validUser.setLastName("Test");
        validUser.setEmail("test-register-valid@example.com");
        validUser.setImageUrl("http://placehold.it/50x50");
        validUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        validUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
        assertThat(userRepository.findOneByLogin("test-register-valid").blockOptional()).isEmpty();

        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(validUser))
            .exchange()
            .expectStatus()
            .isCreated();

        assertThat(userRepository.findOneByLogin("test-register-valid").blockOptional()).isPresent();
    }

    @Test
    void testRegisterInvalidLogin() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM();
        invalidUser.setLogin("funky-log(n"); // <-- invalid
        invalidUser.setPassword("password");
        invalidUser.setFirstName("Funky");
        invalidUser.setLastName("One");
        invalidUser.setEmail("funky@example.com");
        invalidUser.setActivated(true);
        invalidUser.setImageUrl("http://placehold.it/50x50");
        invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(invalidUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        Optional<User> user = userRepository.findOneByEmailIgnoreCase("funky@example.com").blockOptional();
        assertThat(user).isEmpty();
    }

    static Stream<ManagedUserVM> invalidUsers() {
        return Stream.of(
            createInvalidUser("bob", "password", "Bob", "Green", "invalid", true), // <-- invalid
            createInvalidUser("bob", "123", "Bob", "Green", "bob@example.com", true), // password with only 3 digits
            createInvalidUser("bob", null, "Bob", "Green", "bob@example.com", true) // invalid null password
        );
    }

    @ParameterizedTest
    @MethodSource("invalidUsers")
    void testRegisterInvalidUsers(ManagedUserVM invalidUser) throws Exception {
        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(invalidUser))
            .exchange()
            .expectStatus()
            .isBadRequest();

        Optional<User> user = userRepository.findOneByLogin("bob").blockOptional();
        assertThat(user).isEmpty();
    }

    private static ManagedUserVM createInvalidUser(
        String login,
        String password,
        String firstName,
        String lastName,
        String email,
        boolean activated
    ) {
        ManagedUserVM invalidUser = new ManagedUserVM();
        invalidUser.setLogin(login);
        invalidUser.setPassword(password);
        invalidUser.setFirstName(firstName);
        invalidUser.setLastName(lastName);
        invalidUser.setEmail(email);
        invalidUser.setActivated(activated);
        invalidUser.setImageUrl("http://placehold.it/50x50");
        invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
        return invalidUser;
    }

    @Test
    void testRegisterDuplicateLogin() throws Exception {
        // First registration
        ManagedUserVM firstUser = new ManagedUserVM();
        firstUser.setLogin("alice");
        firstUser.setPassword("password");
        firstUser.setFirstName("Alice");
        firstUser.setLastName("Something");
        firstUser.setEmail("alice@example.com");
        firstUser.setImageUrl("http://placehold.it/50x50");
        firstUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        firstUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        // Duplicate login, different email
        ManagedUserVM secondUser = new ManagedUserVM();
        secondUser.setLogin(firstUser.getLogin());
        secondUser.setPassword(firstUser.getPassword());
        secondUser.setFirstName(firstUser.getFirstName());
        secondUser.setLastName(firstUser.getLastName());
        secondUser.setEmail("alice2@example.com");
        secondUser.setImageUrl(firstUser.getImageUrl());
        secondUser.setLangKey(firstUser.getLangKey());
        secondUser.setCreatedBy(firstUser.getCreatedBy());
        secondUser.setCreatedDate(firstUser.getCreatedDate());
        secondUser.setLastModifiedBy(firstUser.getLastModifiedBy());
        secondUser.setLastModifiedDate(firstUser.getLastModifiedDate());
        secondUser.setAuthorities(new HashSet<>(firstUser.getAuthorities()));

        // First user
        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(firstUser))
            .exchange()
            .expectStatus()
            .isCreated();

        // Second (non activated) user
        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(secondUser))
            .exchange()
            .expectStatus()
            .isCreated();

        Optional<User> testUser = userRepository.findOneByEmailIgnoreCase("alice2@example.com").blockOptional();
        assertThat(testUser).isPresent();
        testUser.orElseThrow().setActivated(true);
        userRepository.save(testUser.orElseThrow()).block();

        // Second (already activated) user
        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(secondUser))
            .exchange()
            .expectStatus()
            .isBadRequest();
    }

    @Test
    void testRegisterDuplicateEmail() throws Exception {
        // First user
        ManagedUserVM firstUser = new ManagedUserVM();
        firstUser.setLogin("test-register-duplicate-email");
        firstUser.setPassword("password");
        firstUser.setFirstName("Alice");
        firstUser.setLastName("Test");
        firstUser.setEmail("test-register-duplicate-email@example.com");
        firstUser.setImageUrl("http://placehold.it/50x50");
        firstUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        firstUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        // Register first user
        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(firstUser))
            .exchange()
            .expectStatus()
            .isCreated();

        Optional<User> testUser1 = userRepository.findOneByLogin("test-register-duplicate-email").blockOptional();
        assertThat(testUser1).isPresent();

        // Duplicate email, different login
        ManagedUserVM secondUser = new ManagedUserVM();
        secondUser.setLogin("test-register-duplicate-email-2");
        secondUser.setPassword(firstUser.getPassword());
        secondUser.setFirstName(firstUser.getFirstName());
        secondUser.setLastName(firstUser.getLastName());
        secondUser.setEmail(firstUser.getEmail());
        secondUser.setImageUrl(firstUser.getImageUrl());
        secondUser.setLangKey(firstUser.getLangKey());
        secondUser.setAuthorities(new HashSet<>(firstUser.getAuthorities()));

        // Register second (non activated) user
        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(secondUser))
            .exchange()
            .expectStatus()
            .isCreated();

        Optional<User> testUser2 = userRepository.findOneByLogin("test-register-duplicate-email").blockOptional();
        assertThat(testUser2).isEmpty();

        Optional<User> testUser3 = userRepository.findOneByLogin("test-register-duplicate-email-2").blockOptional();
        assertThat(testUser3).isPresent();

        // Duplicate email - with uppercase email address
        ManagedUserVM userWithUpperCaseEmail = new ManagedUserVM();
        userWithUpperCaseEmail.setId(firstUser.getId());
        userWithUpperCaseEmail.setLogin("test-register-duplicate-email-3");
        userWithUpperCaseEmail.setPassword(firstUser.getPassword());
        userWithUpperCaseEmail.setFirstName(firstUser.getFirstName());
        userWithUpperCaseEmail.setLastName(firstUser.getLastName());
        userWithUpperCaseEmail.setEmail("TEST-register-duplicate-email@example.com");
        userWithUpperCaseEmail.setImageUrl(firstUser.getImageUrl());
        userWithUpperCaseEmail.setLangKey(firstUser.getLangKey());
        userWithUpperCaseEmail.setAuthorities(new HashSet<>(firstUser.getAuthorities()));

        // Register third (not activated) user
        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(userWithUpperCaseEmail))
            .exchange()
            .expectStatus()
            .isCreated();

        Optional<User> testUser4 = userRepository.findOneByLogin("test-register-duplicate-email-3").blockOptional();
        assertThat(testUser4).isPresent();
        assertThat(testUser4.orElseThrow().getEmail()).isEqualTo("test-register-duplicate-email@example.com");

        testUser4.orElseThrow().setActivated(true);
        userService.updateUser((new AdminUserDTO(testUser4.orElseThrow()))).block();

        // Register 4th (already activated) user
        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(secondUser))
            .exchange()
            .expectStatus()
            .is4xxClientError();
    }

    @Test
    void testRegisterAdminIsIgnored() throws Exception {
        ManagedUserVM validUser = new ManagedUserVM();
        validUser.setLogin("badguy");
        validUser.setPassword("password");
        validUser.setFirstName("Bad");
        validUser.setLastName("Guy");
        validUser.setEmail("badguy@example.com");
        validUser.setActivated(true);
        validUser.setImageUrl("http://placehold.it/50x50");
        validUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        validUser.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        accountWebTestClient
            .post()
            .uri("/api/register")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(validUser))
            .exchange()
            .expectStatus()
            .isCreated();

        Optional<User> userDup = userRepository.findOneWithAuthoritiesByLogin("badguy").blockOptional();
        assertThat(userDup).isPresent();
        assertThat(userDup.orElseThrow().getAuthorities())
            .hasSize(1)
            .containsExactly(authorityRepository.findById(AuthoritiesConstants.USER).block());
    }

    @Test
    void testActivateAccount() {
        final String activationKey = "some activation key";
        User user = new User();
        user.setLogin("activate-account");
        user.setEmail("activate-account@example.com");
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(false);
        user.setActivationKey(activationKey);
        user.setCreatedBy(Constants.SYSTEM);

        userRepository.save(user).block();

        accountWebTestClient.get().uri("/api/activate?key={activationKey}", activationKey).exchange().expectStatus().isOk();

        user = userRepository.findOneByLogin(user.getLogin()).block();
        assertThat(user.isActivated()).isTrue();
    }

    @Test
    void testActivateAccountWithWrongKey() {
        accountWebTestClient
            .get()
            .uri("/api/activate?key=wrongActivationKey")
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @WithMockUser("save-account")
    void testSaveAccount() throws Exception {
        User user = new User();
        user.setLogin("save-account");
        user.setEmail("save-account@example.com");
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(true);
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setLogin("not-used");
        userDTO.setFirstName("firstname");
        userDTO.setLastName("lastname");
        userDTO.setEmail("save-account@example.com");
        userDTO.setActivated(false);
        userDTO.setImageUrl("http://placehold.it/50x50");
        userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        accountWebTestClient
            .post()
            .uri("/api/account")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(userDTO))
            .exchange()
            .expectStatus()
            .isOk();

        User updatedUser = userRepository.findOneWithAuthoritiesByLogin(user.getLogin()).block();
        assertThat(updatedUser.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(updatedUser.getLastName()).isEqualTo(userDTO.getLastName());
        assertThat(updatedUser.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(updatedUser.getLangKey()).isEqualTo(userDTO.getLangKey());
        assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(updatedUser.getImageUrl()).isEqualTo(userDTO.getImageUrl());
        assertThat(updatedUser.isActivated()).isTrue();
        assertThat(updatedUser.getAuthorities()).isEmpty();
    }

    @Test
    @WithMockUser("save-invalid-email")
    void testSaveInvalidEmail() throws Exception {
        User user = new User();
        user.setLogin("save-invalid-email");
        user.setEmail("save-invalid-email@example.com");
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(true);
        user.setCreatedBy(Constants.SYSTEM);

        userRepository.save(user).block();

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setLogin("not-used");
        userDTO.setFirstName("firstname");
        userDTO.setLastName("lastname");
        userDTO.setEmail("invalid email");
        userDTO.setActivated(false);
        userDTO.setImageUrl("http://placehold.it/50x50");
        userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        accountWebTestClient
            .post()
            .uri("/api/account")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(userDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertThat(userRepository.findOneByEmailIgnoreCase("invalid email").blockOptional()).isNotPresent();
    }

    @Test
    @WithMockUser("save-existing-email")
    void testSaveExistingEmail() throws Exception {
        User user = new User();
        user.setLogin("save-existing-email");
        user.setEmail("save-existing-email@example.com");
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(true);
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        User anotherUser = new User();
        anotherUser.setLogin("save-existing-email2");
        anotherUser.setEmail("save-existing-email2@example.com");
        anotherUser.setPassword(RandomStringUtils.randomAlphanumeric(60));
        anotherUser.setActivated(true);
        anotherUser.setCreatedBy(Constants.SYSTEM);

        userRepository.save(anotherUser).block();

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setLogin("not-used");
        userDTO.setFirstName("firstname");
        userDTO.setLastName("lastname");
        userDTO.setEmail("save-existing-email2@example.com");
        userDTO.setActivated(false);
        userDTO.setImageUrl("http://placehold.it/50x50");
        userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        accountWebTestClient
            .post()
            .uri("/api/account")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(userDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        User updatedUser = userRepository.findOneByLogin("save-existing-email").block();
        assertThat(updatedUser.getEmail()).isEqualTo("save-existing-email@example.com");
    }

    @Test
    @WithMockUser("save-existing-email-and-login")
    void testSaveExistingEmailAndLogin() throws Exception {
        User user = new User();
        user.setLogin("save-existing-email-and-login");
        user.setEmail("save-existing-email-and-login@example.com");
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(true);
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setLogin("not-used");
        userDTO.setFirstName("firstname");
        userDTO.setLastName("lastname");
        userDTO.setEmail("save-existing-email-and-login@example.com");
        userDTO.setActivated(false);
        userDTO.setImageUrl("http://placehold.it/50x50");
        userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        accountWebTestClient
            .post()
            .uri("/api/account")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(userDTO))
            .exchange()
            .expectStatus()
            .isOk();

        User updatedUser = userRepository.findOneByLogin("save-existing-email-and-login").block();
        assertThat(updatedUser.getEmail()).isEqualTo("save-existing-email-and-login@example.com");
    }

    @Test
    @WithMockUser("change-password-wrong-existing-password")
    void testChangePasswordWrongExistingPassword() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(passwordEncoder.encode(currentPassword));
        user.setLogin("change-password-wrong-existing-password");
        user.setEmail("change-password-wrong-existing-password@example.com");
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        accountWebTestClient
            .post()
            .uri("/api/account/change-password")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(new PasswordChangeDTO("1" + currentPassword, "new password")))
            .exchange()
            .expectStatus()
            .isBadRequest();

        User updatedUser = userRepository.findOneByLogin("change-password-wrong-existing-password").block();
        assertThat(passwordEncoder.matches("new password", updatedUser.getPassword())).isFalse();
        assertThat(passwordEncoder.matches(currentPassword, updatedUser.getPassword())).isTrue();
    }

    @Test
    @WithMockUser("change-password")
    void testChangePassword() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(passwordEncoder.encode(currentPassword));
        user.setLogin("change-password");
        user.setEmail("change-password@example.com");
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        accountWebTestClient
            .post()
            .uri("/api/account/change-password")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(new PasswordChangeDTO(currentPassword, "new password")))
            .exchange()
            .expectStatus()
            .isOk();

        User updatedUser = userRepository.findOneByLogin("change-password").block();
        assertThat(passwordEncoder.matches("new password", updatedUser.getPassword())).isTrue();
    }

    @Test
    @WithMockUser("change-password-too-small")
    void testChangePasswordTooSmall() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(passwordEncoder.encode(currentPassword));
        user.setLogin("change-password-too-small");
        user.setEmail("change-password-too-small@example.com");
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        String newPassword = RandomStringUtils.random(ManagedUserVM.PASSWORD_MIN_LENGTH - 1);

        accountWebTestClient
            .post()
            .uri("/api/account/change-password")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(new PasswordChangeDTO(currentPassword, newPassword)))
            .exchange()
            .expectStatus()
            .isBadRequest();

        User updatedUser = userRepository.findOneByLogin("change-password-too-small").block();
        assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @WithMockUser("change-password-too-long")
    void testChangePasswordTooLong() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(passwordEncoder.encode(currentPassword));
        user.setLogin("change-password-too-long");
        user.setEmail("change-password-too-long@example.com");
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        String newPassword = RandomStringUtils.random(ManagedUserVM.PASSWORD_MAX_LENGTH + 1);

        accountWebTestClient
            .post()
            .uri("/api/account/change-password")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(new PasswordChangeDTO(currentPassword, newPassword)))
            .exchange()
            .expectStatus()
            .isBadRequest();

        User updatedUser = userRepository.findOneByLogin("change-password-too-long").block();
        assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @WithMockUser("change-password-empty")
    void testChangePasswordEmpty() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(passwordEncoder.encode(currentPassword));
        user.setLogin("change-password-empty");
        user.setEmail("change-password-empty@example.com");
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        accountWebTestClient
            .post()
            .uri("/api/account/change-password")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(new PasswordChangeDTO(currentPassword, "")))
            .exchange()
            .expectStatus()
            .isBadRequest();

        User updatedUser = userRepository.findOneByLogin("change-password-empty").block();
        assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void testRequestPasswordReset() {
        User user = new User();
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(true);
        user.setLogin("password-reset");
        user.setEmail("password-reset@example.com");
        user.setLangKey("en");
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        accountWebTestClient
            .post()
            .uri("/api/account/reset-password/init")
            .bodyValue("password-reset@example.com")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    void testRequestPasswordResetUpperCaseEmail() {
        User user = new User();
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setActivated(true);
        user.setLogin("password-reset-upper-case");
        user.setEmail("password-reset-upper-case@example.com");
        user.setLangKey("en");
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        accountWebTestClient
            .post()
            .uri("/api/account/reset-password/init")
            .bodyValue("password-reset-upper-case@EXAMPLE.COM")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    void testRequestPasswordResetWrongEmail() {
        accountWebTestClient
            .post()
            .uri("/api/account/reset-password/init")
            .bodyValue("password-reset-wrong-email@example.com")
            .exchange()
            .expectStatus()
            .isOk();
    }

    @Test
    void testFinishPasswordReset() throws Exception {
        User user = new User();
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setLogin("finish-password-reset");
        user.setEmail("finish-password-reset@example.com");
        user.setResetDate(Instant.now().plusSeconds(60));
        user.setResetKey("reset key");
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey(user.getResetKey());
        keyAndPassword.setNewPassword("new password");

        accountWebTestClient
            .post()
            .uri("/api/account/reset-password/finish")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(keyAndPassword))
            .exchange()
            .expectStatus()
            .isOk();

        User updatedUser = userRepository.findOneByLogin(user.getLogin()).block();
        assertThat(passwordEncoder.matches(keyAndPassword.getNewPassword(), updatedUser.getPassword())).isTrue();
    }

    @Test
    void testFinishPasswordResetTooSmall() throws Exception {
        User user = new User();
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setLogin("finish-password-reset-too-small");
        user.setEmail("finish-password-reset-too-small@example.com");
        user.setResetDate(Instant.now().plusSeconds(60));
        user.setResetKey("reset key too small");
        user.setCreatedBy(Constants.SYSTEM);
        userRepository.save(user).block();

        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey(user.getResetKey());
        keyAndPassword.setNewPassword("foo");

        accountWebTestClient
            .post()
            .uri("/api/account/reset-password/finish")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(keyAndPassword))
            .exchange()
            .expectStatus()
            .isBadRequest();

        User updatedUser = userRepository.findOneByLogin(user.getLogin()).block();
        assertThat(passwordEncoder.matches(keyAndPassword.getNewPassword(), updatedUser.getPassword())).isFalse();
    }

    @Test
    void testFinishPasswordResetWrongKey() throws Exception {
        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey("wrong reset key");
        keyAndPassword.setNewPassword("new password");

        accountWebTestClient
            .post()
            .uri("/api/account/reset-password/finish")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(keyAndPassword))
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
