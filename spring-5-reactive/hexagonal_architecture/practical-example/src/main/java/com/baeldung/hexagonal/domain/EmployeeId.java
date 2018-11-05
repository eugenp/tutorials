package com.baeldung.hexagonal.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class EmployeeId {
	private static AtomicInteger numAllocated = new AtomicInteger(0);
	  private final int id;
	  
	  public EmployeeId() {
	    this.id = numAllocated.incrementAndGet();
	  }

	  public EmployeeId(int id) {
	    this.id = id;
	  }
	  
	  public int getId() {
	    return id;
	  }	  
	  
	  @Override
	  public String toString() {
	    return String.format("%d", id);
	  }
	  @Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }

	    EmployeeId that = (EmployeeId) o;

	    return id == that.id;

	  }

	  @Override
	  public int hashCode() {
	    return id;
	  }
}
