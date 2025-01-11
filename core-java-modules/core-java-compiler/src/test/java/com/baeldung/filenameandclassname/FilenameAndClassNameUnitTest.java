package com.baeldung.filenameandclassname;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FilenameAndClassNameUnitTest {

    @Test
    void whenNoPublicClassInSourceFile_thenCorrect() {
        Club manUnited = new Club("Manchester United F.C.");
        FootballPlayer rooney = new FootballPlayer("Wayne Rooney", manUnited);

        assertEquals("Wayne Rooney", rooney.getName());
        assertEquals("Manchester United F.C.", rooney.getClub() .getName());
    }

}