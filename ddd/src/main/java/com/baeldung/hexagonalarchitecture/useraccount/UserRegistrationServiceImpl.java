package com.baeldung.hexagonalarchitecture.useraccount;

import com.baeldung.hexagonalarchitecture.configuration.UserAccountConfigurableProperties;
import com.baeldung.hexagonalarchitecture.emailing.EmailRequest;
import com.baeldung.hexagonalarchitecture.emailing.EmailingService;
import com.baeldung.hexagonalarchitecture.error.InvalidRequestException;
import com.baeldung.hexagonalarchitecture.error.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 13:40
 */
@Service
class UserRegistrationServiceImpl implements UserRegistrationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final UserAccountConfigurableProperties properties;
    private final EmailingService emailingService;

    UserRegistrationServiceImpl(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder,
                                MessageSource messageSource, UserAccountConfigurableProperties properties,
                                EmailingService emailingService) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
        this.properties = properties;
        this.emailingService = emailingService;
    }

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest) {
        LOGGER.info("Registering user: {}", userRegistrationRequest);
        Objects.requireNonNull(userRegistrationRequest, "userRegistrationRequest cannot be null");

        if (userAccountRepository.findByUsername(userRegistrationRequest.getUsername().toLowerCase()).isPresent()) {
            throw new InvalidRequestException("Username is taken");
        }

        String encryptedPassword = passwordEncoder.encode(userRegistrationRequest.getPassword());
        UserAccount userAccount = UserAccount.of(userRegistrationRequest.getUsername().toLowerCase(), encryptedPassword,
                UserAccountEmail.of(userRegistrationRequest.getEmail()));

        userAccount = userAccountRepository.save(userAccount);
        LOGGER.info("Successfully created new user account {}", userAccount);
        sendConfirmationEmail(userAccount, userRegistrationRequest.getLogInUrl());
        return UserRegistrationResponse.of("You have successfully signed up, kindly check your email to complete the process");
    }


    private void sendConfirmationEmail(UserAccount userAccount, String logInUrl) {
        LOGGER.info("Sending confirmation email request {}", userAccount);
        String confirmationUrl = this.getEncodedEmailVerificationUri(userAccount.getUsername(),
                userAccount.getVerificationToken(), logInUrl);
        Object[] emailBodyParameters = {userAccount.getUsername(), confirmationUrl};
        String emailBody = messageSource.getMessage("email-confirmation-body", emailBodyParameters,
                LocaleContextHolder.getLocale());
        EmailRequest emailRequest = EmailRequest.of("Activate Your Account", emailBody, userAccount
                .getEmail().getEmail());
        emailingService.sendEmail(emailRequest);
    }

    private String getEncodedEmailVerificationUri(String username, String activationToken, String logInUrl) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("username", username);
        requestParams.put("token", activationToken);
        requestParams.put("logInUrl", logInUrl);
        return encodeUrl(requestParams, properties.getEmailConfirmationUri());
    }

    private String encodeUrl(Map<String, String> requestParameters, String baseUrl) {
        return requestParameters.keySet().stream()
                .map(key -> key + "=" + UriUtils.encode(requestParameters.get(key), StandardCharsets.UTF_8))
                .collect(joining("&", UriUtils.encodePath(baseUrl, "UTF-8") + "?", ""));

    }

    @Override
    public EmailConfirmationResponse confirmEmail(EmailConfirmationRequest emailConfirmationRequest) {
        LOGGER.info("Confirming user email: {}", emailConfirmationRequest);
        Objects.requireNonNull(emailConfirmationRequest, "emailConfirmationRequest cannot be null");
        UserAccount userAccount = userAccountRepository.findByUsername(emailConfirmationRequest.getUsername())
                .orElseThrow(() -> new RecordNotFoundException("User account was not found."));
        if (Objects.isNull(userAccount)) {
            throw new RecordNotFoundException("User sign up record was not found");
        }

        if (userAccount.getEmail().isConfirmed()) {
            throw new InvalidRequestException("Email is already confirmed");
        }

        if (userAccount.getVerificationToken().equals(emailConfirmationRequest.getToken())) {
            userAccount.getEmail().setConfirmed(true);
            userAccount = userAccountRepository.save(userAccount);
            LOGGER.info("Successfully confirmed email: {}", userAccount);
            return EmailConfirmationResponse.of("Your email was successfully confirmed.");
        } else {
            throw new InvalidRequestException("Incorrect token");
        }
    }
}
