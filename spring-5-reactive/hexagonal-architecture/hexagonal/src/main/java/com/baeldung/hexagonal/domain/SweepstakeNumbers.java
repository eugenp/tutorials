package com.baeldung.hexagonal.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Joiner;

public class SweepstakeNumbers {
	private final Set<Integer> numbers;
	  
	  public static final int MIN_NUMBER = 1;
	  public static final int MAX_NUMBER = 20;
	  public static final int NUM_NUMBERS = 4;

	  /**
	   * Constructor. Creates random SweepstakeNumbers.
	   */
	  private SweepstakeNumbers() {
	    numbers = new HashSet<>();
	    generateRandomNumbers();
	  }

	  /**
	   * Constructor. Uses given numbers.
	   */
	  private SweepstakeNumbers(Set<Integer> givenNumbers) {
	    numbers = new HashSet<>();
	    numbers.addAll(givenNumbers);
	  }

	  /**
	   * @return random SweepstakeNumbers
	   */
	  public static SweepstakeNumbers createRandom() {
	    return new SweepstakeNumbers();
	  }

	  /**
	   * @return given SweepstakeNumbers
	   */
	  public static SweepstakeNumbers create(Set<Integer> givenNumbers) {
	    return new SweepstakeNumbers(givenNumbers);
	  }
	  
	  /**
	   * @return SweepstakeNumbers
	   */
	  public Set<Integer> getNumbers() {
	    return Collections.unmodifiableSet(numbers);
	  }

	  /**
	   * @return numbers as comma separated string
	   */
	  public String getNumbersAsString() {
	    return Joiner.on(',').join(numbers);
	  }
	  
	  /**
	   * Generates 4 unique random numbers between 1-20 into numbers set.
	   */
	  private void generateRandomNumbers() {
	    numbers.clear();
	    RandomNumberGenerator generator = new RandomNumberGenerator(MIN_NUMBER, MAX_NUMBER);
	    while (numbers.size() < NUM_NUMBERS) {
	      int num = generator.nextInt();
	      if (!numbers.contains(num)) {
	        numbers.add(num);
	      }
	    }
	  }

	  @Override
	  public String toString() {
	    return "SweepstakeNumbers{" + "numbers=" + numbers + '}';
	  }

	  /**
	   * 
	   * Helper class for generating random numbers.
	   *
	   */
	  private static class RandomNumberGenerator {

	    private PrimitiveIterator.OfInt randomIterator;

	    /**
	     * Initialize a new random number generator that generates random numbers in the range [min, max]
	     * 
	     * @param min the min value (inclusive)
	     * @param max the max value (inclusive)
	     */
	    public RandomNumberGenerator(int min, int max) {
	      randomIterator = new Random().ints(min, max + 1).iterator();
	    }

	    /**
	     * @return a random number in the range (min, max)
	     */
	    public int nextInt() {
	      return randomIterator.nextInt();
	    }
	  }
	  
	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((numbers == null) ? 0 : numbers.hashCode());
	    return result;
	  }

	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj) {
	      return true;
	    }
	    if (obj == null) {
	      return false;
	    }
	    if (getClass() != obj.getClass()) {
	      return false;
	    }
	    SweepstakeNumbers other = (SweepstakeNumbers) obj;
	    if (numbers == null) {
	      if (other.numbers != null) {
	        return false;
	      }
	    } else if (!numbers.equals(other.numbers)) {
	      return false;
	    }
	    return true;
	  }  
}
