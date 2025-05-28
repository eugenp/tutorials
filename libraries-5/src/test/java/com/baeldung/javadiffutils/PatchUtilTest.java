package com.baeldung.javadiffutils;

import com.github.difflib.patch.PatchFailedException;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class PatchUtilTest {
    @Test
    public void givenPatch_whenApplied_thenMatchesRevised() throws PatchFailedException {
        var original = List.of("alpha", "beta", "gamma");
        var revised = List.of("alpha", "beta-updated", "gamma");
        var result = new PatchUtil().apply(original, revised);
        assertEquals(revised, result);
    }
}
