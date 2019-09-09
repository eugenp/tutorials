package groovy.com.baeldung.stringtypes

import org.junit.Assert
import org.junit.Test

class CharacterInGroovy {

    @Test
    void 'character'() {
        char a = 'A' as char
        char b = 'B' as char
        char c = (char) 'C'

        Assert.assertTrue(a instanceof Character)
        Assert.assertTrue(b instanceof Character)
        Assert.assertTrue(c instanceof Character)
    }

}
