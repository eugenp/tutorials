package com.apps.shallowcopyvsdeepcopy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ShallowCopyUnitTest {
  @Test 
public void whenModifyingOriginalObject_ThenCopyShouldChange() { 
    
    Fruit myFruit = new Fruit(7, new String[]{"Apple", "Watermelon", "Orange", "Pear", "Cherry", "Strawberry", "Grape"}, "simple fruit"); 
    Fruit cloneOfMyFruit = (Fruit)myFruit.clone(); 
    myFruit.fruits[4] = "Lemon"; 
    assertThat(myFruit.fruits[4]).isEqualTo(cloneOfMyFruit.fruits[4]);
    
}

}