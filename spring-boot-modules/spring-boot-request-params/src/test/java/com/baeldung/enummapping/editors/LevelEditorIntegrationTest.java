package com.baeldung.enummapping.editors;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.baeldung.enummapping.enums.Level;

public class LevelEditorIntegrationTest {

    private final LevelEditor levelEditor = new LevelEditor();

    @Test
    public void whenConvertStringToLevelEnumUsingCustomPropertyEditor_thenSuccess() {
        levelEditor.setAsText("lOw");

        assertThat(levelEditor.getValue()).isEqualTo(Level.LOW);
    }

    @Test
    public void whenStringIsEmpty_thenReturnNull() {
        levelEditor.setAsText("");

        assertThat(levelEditor.getValue()).isNull();
    }

    @Test
    public void whenStringIsNull_thenReturnNull() {
        levelEditor.setAsText(null);

        assertThat(levelEditor.getValue()).isNull();
    }

}
