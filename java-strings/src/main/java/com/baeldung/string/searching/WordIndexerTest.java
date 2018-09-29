package BAEL2142;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordIndexerTest {

    String theString;
    String theExpected;
    WordIndexer wordIndexer;

    @Before
    public void setUp() throws Exception {
        wordIndexer = new WordIndexer();
        theString = "To be, or not to be: that is the question:\n"
          + "Whether 'tis nobler in the mind to suffer\n"
          + "The slings and arrows of outrageous fortune,\n"
          + "Or to take arms against a sea of troubles,\n"
          + "And by opposing end them? To die: to sleep;\n"
          + "No more; and by a sleep to say we end\n"
          + "The heart-ache and the thousand natural shocks\n"
          + "That flesh is heir to, 'tis a consummation\n"
          + "Devoutly to be wish'd. To die, to sleep;\n"
          + "To sleep: perchance to dream: ay, there's the rub;\n";

        theExpected = "0 To be, or not to \n" +
                "14 to be: that is th\n" +
                "75 to suffer\n" +
                "The sli\n" +
                "133 to take arms agai\n" +
                "199 To die: to sleep;\n" +
                "207 to sleep;\n" +
                "No more\n" +
                "241 to say we end\n" +
                "The\n" +
                "321 to, 'tis a consum\n" +
                "354 to be wish'd. To \n" +
                "368 To die, to sleep;\n" +
                "376 to sleep;\n" +
                "To slee\n" +
                "386 To sleep: perchan\n" +
                "406 to dream: ay, the\n" +
                "\n" +
                "The string 'to' was found 13 times.";
    }

    @Test
    public void findWord() {
        String output = wordIndexer.findWord(theString, "to");
        System.out.println(output);
        assertEquals(output, theExpected);
    }
}