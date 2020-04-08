/*
  ------------------------------------------------------------------------------
        (c) by data experts gmbh
              Postfach 1130
              Woldegker Str. 12
              17001 Neubrandenburg

  Dieses Dokument und die hierin enthaltenen Informationen unterliegen
  dem Urheberrecht und duerfen ohne die schriftliche Genehmigung des
  Herausgebers weder als ganzes noch in Teilen dupliziert oder reproduziert
  noch manipuliert werden.
*/
package com.baeldung.javahexagonal.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EventUnitTest {

    @Test
    void id_must_not_be_null() {
        assertThatThrownBy(() -> new Event(null, "Introduction to JUnit")).hasMessage("id must not be null");
    }

    @Test
    void name_must_not_be_null() {
        assertThatThrownBy(() -> new Event("junit", null)).hasMessage("name must not be null");
    }

    @Test
    void allows_valid_id_and_valid_name() {
        assertThatCode(() -> new Event("junit", "Introduction to JUnit")).doesNotThrowAnyException();
    }
}
