package com.baeldung.hexagonal;

import com.baeldung.hexagonal.saving.adapter.out.persistence.SavingAccountRepositoryAdapter;
import com.baeldung.hexagonal.saving.domain.SavingAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationIntegrationTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void givenSavingAccount_whenGetBalancefromPersistenceAdapter_thenReturnBalance() {
        // given
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAccountNumber("123456");
        savingAccount.setBalance(BigDecimal.valueOf(500));
        savingAccountRepository.addSavingAccount(savingAccount);

        // when
        BigDecimal balance = savingAccountRepository.getBalance("123456");

        // then
        assertEquals(BigDecimal.valueOf(500), balance);
    }

    @Test
    void givenSavingAccount_whenGetBalancefromWebAdapter_thenReturnBalance() throws Exception {
        // given
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAccountNumber("123456");
        savingAccount.setBalance(BigDecimal.valueOf(500));
        savingAccountRepository.addSavingAccount(savingAccount);

        // when
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/savingAccount/balance/123456";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        // then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("500", result.getBody());
    }

}
