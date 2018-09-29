package BAEL2142;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class WordIndexerTest {

    String theString;
    String theExpected;
    WordIndexer wordIndexer;

    @BeforeEach
    public void setUp() throws Exception {
        wordIndexer = new WordIndexer();

        theString = "To be, or not to be: that is the question:"
          + "Whether 'tis nobler in the mind to suffer"
          + "The slings and arrows of outrageous fortune,"
          + "Or to take arms against a sea of troubles,"
          + "And by opposing end them? To die: to sleep;"
          + "No more; and by a sleep to say we end"
          + "The heart-ache and the thousand natural shocks"
          + "That flesh is heir to, 'tis a consummation"
          + "Devoutly to be wish'd. To die, to sleep;"
          + "To sleep: perchance to dream: ay, there's the rub;";

        theExpected = "0 To be, or not to \n"
          + "14 to be: that is th\n"
          + "74 to sufferThe slin\n"
          + "130 to take arms agai\n"
          + "195 To die: to sleep;\n"
          + "203 to sleep;No more;\n"
          + "236 to say we endThe \n"
          + "314 to, 'tis a consum\n"
          + "346 to be wish'd. To \n"
          + "360 To die, to sleep;\n"
          + "368 to sleep;To sleep\n"
          + "377 To sleep: perchan\n"
          + "397 to dream: ay, the\n"
          + "\n"
          + "The string 'to' was found 13 times.";
    }

    @Test
    public void findWord() {
        String output = wordIndexer.findWord(theString, "to");
        System.out.println(output);
        assertEquals(output, theExpected);
    }
}