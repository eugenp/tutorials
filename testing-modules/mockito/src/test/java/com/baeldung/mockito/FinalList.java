package com.baeldung.mockito;

import com.baeldung.mockito.voidmethods.MyList;

public final class FinalList extends MyList {

  @Override
  public int size() {
    return 1;
  }

}
