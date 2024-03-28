package com.baeldung.iteratorvsforeach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IteratorVsForeach {

  private static List<String> generateSampleList() {
    return new ArrayList<>(Arrays.asList("String1", "String2", "unwanted"));
  }

  public static void main(String[] args) {
    List<String> list = generateSampleList();
    Iterator<String> it = list.iterator();
    while (it.hasNext()) {
      String item = it.next();
      if (item.equals("unwanted")) {
        it.remove(); // Safely remove item
      }
    }

    list = generateSampleList();
    list.forEach(item -> {
          if (item.equals("unwanted")) {
            // Direct removal will cause a compilation error
            // list.remove(item);
          }
        }
    );

    // Separate collection for items to be removed
    List<String> toRemove = new ArrayList<>();

    // Using forEach to identify items to remove
    list.forEach(item -> {
      if (item.equals("unwanted")) {
        toRemove.add(item);
      }
    });

    // Removing the identified items from the original list
    list.removeAll(toRemove);

  }

}
