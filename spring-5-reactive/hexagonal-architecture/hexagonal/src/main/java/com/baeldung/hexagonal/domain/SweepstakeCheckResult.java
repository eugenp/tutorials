package com.baeldung.hexagonal.domain;

public class SweepstakeCheckResult {
	/**
	   * Enumeration of Type of Outcomes of a Sweepstake
	   */
	  public enum CheckResult { WIN_PRIZE, NO_PRIZE, TICKET_NOT_SUBMITTED }

	  private final CheckResult checkResult;
	  private final int prizeAmount;

	  /**
	   * Constructor.
	   */
	  public SweepstakeCheckResult(CheckResult result) {
	    checkResult = result;
	    prizeAmount = 0;
	  }

	  /**
	   * Constructor.
	   */
	  public SweepstakeCheckResult(CheckResult result, int amount) {
	    checkResult = result;
	    prizeAmount = amount;
	  }

	  /**
	   * @return check result
	   */
	  public CheckResult getResult() {
	    return checkResult;
	  }

	  /**
	   * @return prize amount
	   */
	  public int getPrizeAmount() {
	    return prizeAmount;
	  }

	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((checkResult == null) ? 0 : checkResult.hashCode());
	    result = prime * result + prizeAmount;
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
	    SweepstakeCheckResult other = (SweepstakeCheckResult) obj;
	    return checkResult == other.checkResult && prizeAmount == other.prizeAmount;
	  }
}
