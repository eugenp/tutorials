package com.baeldung.string.searching;

public class WordIndexer {

    public static void main(String[] args) {
        String theString = "To be, or not to be: that is the question:\n" +
                "Whether 'tis nobler in the mind to suffer\n" +
                "The slings and arrows of outrageous fortune,\n" +
                "Or to take arms against a sea of troubles,\n" +
                "And by opposing end them? To die: to sleep;\n" +
                "No more; and by a sleep to say we end\n" +
                "The heart-ache and the thousand natural shocks\n" +
                "That flesh is heir to, 'tis a consummation\n" +
                "Devoutly to be wish'd. To die, to sleep;\n" +
                "To sleep: perchance to dream: ay, there's the rub;\n" +
                "For in that sleep of death what dreams may come\n" +
                "When we have shuffled off this mortal coil,\n" +
                "Must give us pause: there's the respect\n" +
                "That makes calamity of so long life;\n" +
                "For who would bear the whips and scorns of time,\n" +
                "The oppressor's wrong, the proud man's contumely,\n" +
                "The pangs of despised love, the law's delay,\n" +
                "The insolence of office and the spurns\n" +
                "That patient merit of the unworthy takes,\n" +
                "When he himself might his quietus make\n" +
                "With a bare bodkin? who would fardels bear,\n" +
                "To grunt and sweat under a weary life,\n" +
                "But that the dread of something after death,\n" +
                "The undiscover'd country from whose bourn\n" +
                "No traveller returns, puzzles the will\n" +
                "And makes us rather bear those ills we have\n" +
                "Than fly to others that we know not of?\n" +
                "Thus conscience does make cowards of us all;\n" +
                "And thus the native hue of resolution\n" +
                "Is sicklied o'er with the pale cast of thought,\n" +
                "And enterprises of great pith and moment\n" +
                "With this regard their currents turn awry,\n" +
                "And lose the name of action.--Soft you now!\n" +
                "The fair Ophelia! Nymph, in thy orisons\n" +
                "Be all my sins remember'd!";

        WordIndexer wordIndexer = new WordIndexer();
        wordIndexer.findWord(theString, "to");
    }


    public void findWord(String textString, String word) {
        int index = -1;
        int count = 0;
        boolean done = false;
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();

        while(!done){

            index = lowerCaseTextString.indexOf(lowerCaseWord, index + 1);
            if (index != -1) {
                count += 1;
                String foundString = textString.substring(index);
                System.out.printf(index + " " + foundString.substring(0, foundString.indexOf("\n")) + "\n" );
            }else{
                done = true;
            }
        }
        System.out.printf("\nThe string '" + word + "' was found %d times.\n", count);
    }
}
