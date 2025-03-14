package com.baeldung.tutorials.passkey;

import com.baeldung.tutorials.passkey.repository.PasskeyCredentialRepository;
import com.baeldung.tutorials.passkey.repository.PasskeyUserRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.test.context.ActiveProfiles;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
@ActiveProfiles("local")
public class PassKeyApplicationLiveTest {

    @LocalServerPort
    int port;

    @Autowired
    PasskeyCredentialRepository credentialRepository;

    @Autowired
    PasskeyUserRepository userRepository ;

    @Autowired
    InMemoryUserDetailsManager userDetailsService;

    WebDriver driver;
    VirtualAuthenticator authenticator;

    @BeforeAll
    static void setupClass() {
        log.info("Setting up driver...");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        log.info("Setting up webdriver...");

        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
          .setIsUserVerified(true)
          .setIsUserConsenting(true)
          .setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2)
          .setHasUserVerification(true)
          .setHasResidentKey(true);

        driver = new ChromeDriver();
        authenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options);
    }

    @AfterEach
    void teardown() {
        if ( driver != null ) {
            driver.quit();
        }
    }

    @Test
    public void whenRegisterNewPasskey_thenSuccess() {
        // Test login with valid user

        String username = createRandomString(8);

        authenticator.getCredentials();

        credentialRepository.deleteAll();
        userRepository.deleteAll();
        userDetailsService.createUser(User.builder()
          .username(username)
          .password("{noop}changeit")
          .authorities("USER")
          .build());

        var baseUrl = "http://localhost:" + port;
        driver.navigate().to(baseUrl);

        WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("changeit");

        WebElement loginButton = driver.findElement(By.cssSelector("form.login-form button[type='submit']"));
        loginButton.click();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> d.findElement(By.id("current-username")).isDisplayed());

        driver.navigate().to(baseUrl + "/webauthn/register");
        wait.until(d -> d.findElement(By.id("register")).isDisplayed());

        driver.findElement(By.id("label")).sendKeys(username);
        driver.findElement(By.id("register")).click();

        wait.until(d -> d.findElement(By.cssSelector("table tr td form.delete-form")).isDisplayed());

        driver.navigate().to(baseUrl + "/logout");
        WebElement logoutButton = driver.findElement(By.cssSelector("form.logout-form button[type='submit']"));
        logoutButton.click();

        // A new credential should be created on the server side
        var count = credentialRepository.count();
        assertEquals(1, count);

        // ...And also on the client side
        var credentials = authenticator.getCredentials();
        assertEquals(1, credentials.size());

        // The client side user handle should match the created user on the server side
        var credential = credentials.getFirst();
        var userHandle = new Bytes(credential.getUserHandle());
        var user = userRepository.findByExternalId(userHandle.toBase64UrlString()).orElseThrow();
        assertEquals(username, user.getName());

        // Try to login using the created passkey
        driver.navigate().to(baseUrl);

        WebElement passkeyLoginButton = driver.findElement(By.id("passkey-signin"));
        passkeyLoginButton.click();
        wait.until(d -> d.findElement(By.id("current-username")).isDisplayed());

    }

    private static String createRandomString(int len) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int index = (int) (chars.length() * Math.random());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    @SneakyThrows
    private static PKCS8EncodedKeySpec createPKCS8EncodedKeySpec() {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(256);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        return new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
    }
}