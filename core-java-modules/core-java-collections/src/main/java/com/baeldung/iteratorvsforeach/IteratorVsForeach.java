package com.baeldung.iteratorvsforeach;

import com.baeldung.synchronizedcollections.Application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class IteratorVsForeach {

  private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
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
    try {
      for (String item : list) {
        if (item.equals("unwanted")) {
          // Direct removal will cause an exception
          list.remove(item);
        }
      }
    } catch (ConcurrentModificationException e) {
      LOGGER.info("This is the expected exception");
    }
  }

}
