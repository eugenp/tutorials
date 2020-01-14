package com.baeldung.stringconcatenation;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class StringConcatenationUnitTest {

  @Test
  public void givenMultipleStrings_whenConcatUsingStringBuilder_checkStringCorrect() {

    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append("Baeldung");
    stringBuilder.append(" is");
    stringBuilder.append(" awesome");

    assertEquals("Baeldung is awesome", stringBuilder.toString());

  }

  @Test
  public void givenMultipleString_whenConcatUsingAdditionOperator_checkStringCorrect() {

    String myString = "The " + "quick " + "brown " + "fox...";

    assertEquals("The quick brown fox...", myString);
  }


  @Test
  public void givenMultipleStrings_whenConcatUsingStringFormat_checkStringCorrect() {

    String myString = String.format("%s %s %.2f %s %s, %s...", "I",
            "ate",
            2.5056302,
            "blueberry",
            "pies",
            "oops");


    assertEquals("I ate 2,51 blueberry pies, oops...", myString);
  }

  @Test
  public void givenMultipleStrings_whenStringConcatUsed_checkStringCorrect() {

    String myString = "Both".concat(" fickle")
            .concat(" dwarves")
            .concat(" jinx")
            .concat(" my")
            .concat(" pig")
            .concat(" quiz");

    assertEquals("Both fickle dwarves jinx my pig quiz", myString);


  }

  @Test
  public void givenMultipleStrings_whenStringJoinUsed_checkStringCorrect() {

    String[] strings = {"I'm", "running", "out", "of", "pangrams!"};

    String myString = String.join(" ", strings);

    assertEquals("I'm running out of pangrams!", myString);

  }

  @Test
  public void givenMultipleStrings_whenStringJoinerUsed_checkStringCorrect() {

    StringJoiner fruitJoiner = new StringJoiner(", ");
    fruitJoiner.add("Apples");
    fruitJoiner.add("Oranges");
    fruitJoiner.add("Bananas");

    assertEquals("Apples, Oranges, Bananas", fruitJoiner.toString());
  }

  @Test
  public void givenMultipleStrings_whenArrayJoiner_checkStringCorrect() {

    String[] myFavouriteLanguages = {"Java", "JavaScript", "Python"};

    String toString = Arrays.toString(myFavouriteLanguages);

    assertEquals("[Java, JavaScript, Python]", toString);
  }

  @Test
  public void givenArrayListOfStrings_whenCollectorsJoin_checkStringCorrect() {

    List<String> awesomeAnimals = Arrays.asList("Shark", "Panda", "Armadillo");

    String animalString = awesomeAnimals.stream().collect(Collectors.joining(", "));

    assertEquals("Shark, Panda, Armadillo", animalString);
  }
}
