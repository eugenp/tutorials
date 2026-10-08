package com.baeldung.auth.server.customclaims.components;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserInfoMapperTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private UserInfoService userInfoService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private OidcUserInfoAuthenticationContext context;

    @InjectMocks
    private UserInfoMapper userInfoMapper;

    @Test
    void givenContextWithUnknownUser_whenApply_thenReturnUserInfoWithOnlySubject() {

        when(userInfoService.getUserInfoByUsername("unknown_user")).thenReturn(Optional.empty());
        when(context.getAuthentication().getName()).thenReturn("unknown_user");

        var result = userInfoMapper.apply(context);

        assertNotNull(result);
        assertEquals("unknown_user", result.getSubject());
    }

    @Test
    void givenContextWithKnownUserAndNoScopes_whenApply_thenReturnUserInfoWithOnlySubject() {

        when(userInfoService.getUserInfoByUsername("user")).thenReturn(Optional.of(ALICE));
        when(context.getAuthentication()
          .getName()).thenReturn("user");
        when(context.getAuthorization()
          .getAuthorizedScopes()).thenReturn(Set.of());

        var result = userInfoMapper.apply(context);
        assertNotNull(result);
        assertTrue(result.getClaims().containsKey("sub"));
        assertEquals("user", result.getSubject());
        assertEquals(1, result.getClaims().size());

    }

    @Test
    void givenContextWithKnownUserAndStandardScopes_whenApply_thenReturnUserInfoWithOnlyProfileClaims() {

        when(userInfoService.getUserInfoByUsername("user")).thenReturn(Optional.of(ALICE));
        when(context.getAuthentication()
          .getName()).thenReturn("user");
        when(context.getAuthorization()
          .getAuthorizedScopes()).thenReturn(Set.of("openid","profile","email"));

        var result = userInfoMapper.apply(context);
        assertNotNull(result);
        var claims = result.getClaims();

        var expectedClaims = Set.of(
          "sub", "name", "given_name", "family_name","email","email_verified","locale",
          "gender","birthdate","zoneinfo","preferred_username", "updated_at");
        assertTrue(claims.keySet().containsAll(expectedClaims));

        // Ensure that account claims are *not* present
        assertFalse(claims.containsKey("account"));

    }

    @Test
    void givenContextWithKnownUserAndAccountScope_whenApply_thenReturnUserInfoWithAccountClaims() {

        when(userInfoService.getUserInfoByUsername("user")).thenReturn(Optional.of(ALICE));
        when(context.getAuthentication()
          .getName()).thenReturn("user");
        when(context.getAuthorization()
          .getAuthorizedScopes()).thenReturn(Set.of("account"));

        var result = userInfoMapper.apply(context);
        assertNotNull(result);
        var claims = result.getClaims();

        var expectedClaims = Set.of("account_id", "created_at", "account_expires_at");
        assertTrue(claims.keySet().containsAll(expectedClaims));

        // Ensure that email and name claims are *not *present
        assertFalse(claims.containsKey("email"));
        assertFalse(claims.containsKey("name"));

    }

    // Sample user for testing
    private  static final UserInfoService.UserInfo ALICE = new UserInfoService.UserInfo(
      "user", "Alice Smith", "Alice", "Smith",
      "user@example.com", true,
      Locale.forLanguageTag("en-US"),
      "female",
      LocalDate.of(1990, 1, 1),
      ZoneId.of("America/New_York"),
      UUID.randomUUID(),
      Instant.now(),
      Instant.now(),
      Instant.now().plus(60, ChronoUnit.DAYS));

}