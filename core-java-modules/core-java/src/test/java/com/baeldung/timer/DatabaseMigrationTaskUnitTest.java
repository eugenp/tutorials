package com.baeldung.timer;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseMigrationTaskUnitTest {
    @Test
    void givenDatabaseMigrationTask_whenTimerScheduledForNowPlusTwoSeconds_thenDataMigratedAfterTwoSeconds() throws Exception {
        List<String> oldDatabase = Arrays.asList("Harrison Ford", "Carrie Fisher", "Mark Hamill");
        List<String> newDatabase = new ArrayList<>();

        LocalDateTime twoSecondsLater = LocalDateTime.now().plusSeconds(2);
        Date twoSecondsLaterAsDate = Date.from(twoSecondsLater.atZone(ZoneId.systemDefault()).toInstant());

        new Timer().schedule(new DatabaseMigrationTask(oldDatabase, newDatabase), twoSecondsLaterAsDate);

        while (LocalDateTime.now().isBefore(twoSecondsLater)) {
            assertThat(newDatabase).isEmpty();
            Thread.sleep(500);
        }
        assertThat(newDatabase).containsExactlyElementsOf(oldDatabase);
    }

    @Test
    void givenDatabaseMigrationTask_whenTimerScheduledInTwoSeconds_thenDataMigratedAfterTwoSeconds() throws Exception {
        List<String> oldDatabase = Arrays.asList("Harrison Ford", "Carrie Fisher", "Mark Hamill");
        List<String> newDatabase = new ArrayList<>();

        new Timer().schedule(new DatabaseMigrationTask(oldDatabase, newDatabase), 2000);

        LocalDateTime twoSecondsLater = LocalDateTime.now().plusSeconds(2);

        while (LocalDateTime.now().isBefore(twoSecondsLater)) {
            assertThat(newDatabase).isEmpty();
            Thread.sleep(500);
        }
        assertThat(newDatabase).containsExactlyElementsOf(oldDatabase);
    }
}