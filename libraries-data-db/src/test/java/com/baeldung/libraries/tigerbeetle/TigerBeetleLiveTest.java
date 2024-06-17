package com.baeldung.libraries.tigerbeetle;

import com.baeldung.libraries.tigerbeetle.config.TigerBeetleConfig;
import com.baeldung.libraries.tigerbeetle.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = { TigerBeetleConfig.class, AccountRepository.class})
public class TigerBeetleLiveTest {

    @Autowired
    private AccountRepository repo;

    @Test
    void whenCreateAccount_thenSuccess() throws Exception {

        var id = UUID.randomUUID();

        var acc = repo.createAccount(
          id,
          BigInteger.valueOf(99999999L),
          (short) 1000,
          1);

        assertNotNull(acc);
        assertEquals(BigInteger.ZERO, acc.creditsPending());
        assertEquals(BigInteger.ZERO, acc.creditsPosted());
        assertEquals(BigInteger.ZERO, acc.debtsPending());
        assertEquals(BigInteger.ZERO, acc.debtsPosted());
    }

}
