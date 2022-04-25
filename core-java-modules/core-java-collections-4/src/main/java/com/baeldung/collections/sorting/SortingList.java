package com.baeldung.collections.sorting;

import java.util.Date;

public class SortingList {

  public static class Example implements Comparable<Employee> {

    private Date dateTime;

    public Date getDateTime() {
      return dateTime;
    }

    public void setDateTime(Date datetime) {
      this.dateTime = datetime;
    }

    @Override
    public int compareTo(MyObject o) {
      return getDateTime().compareTo(o.getDateTime());
    }
  }

}
