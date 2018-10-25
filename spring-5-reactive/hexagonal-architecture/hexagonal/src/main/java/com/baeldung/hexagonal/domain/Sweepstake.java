package com.baeldung.hexagonal.domain;

public class Sweepstake {
	 private SweepstakeId id;
	  private final PlayerDetails playerDetails;
	  private final SweepstakeNumbers sweepstakeNumbers;

	  /**
	   * Constructor.
	   */
	  public Sweepstake(SweepstakeId id, PlayerDetails details, SweepstakeNumbers numbers) {
	    this.id = id;
	    playerDetails = details;
	    sweepstakeNumbers = numbers;
	  }

	  /**
	   * @return player details
	   */
	  public PlayerDetails getPlayerDetails() {
	    return playerDetails;
	  }
	  
	  /**
	   * @return lottery numbers
	   */
	  public SweepstakeNumbers getNumbers() {
	    return sweepstakeNumbers;
	  }

	  /**
	   * @return id
	   */
	  public SweepstakeId getId() {
	    return id;
	  }

	  /**
	   * set id
	   */
	  public void setId(SweepstakeId id) {
	    this.id = id;
	  }

	  @Override
	  public String toString() {
	    return playerDetails.toString() + " " + sweepstakeNumbers.toString();
	  }

	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((sweepstakeNumbers == null) ? 0 : sweepstakeNumbers.hashCode());
	    result = prime * result + ((playerDetails == null) ? 0 : playerDetails.hashCode());
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
	    Sweepstake other = (Sweepstake) obj;
	    if (sweepstakeNumbers == null) {
	      if (other.sweepstakeNumbers != null) {
	        return false;
	      }
	    } else if (!sweepstakeNumbers.equals(other.sweepstakeNumbers)) {
	      return false;
	    }
	    if (playerDetails == null) {
	      if (other.playerDetails != null) {
	        return false;
	      }
	    } else if (!playerDetails.equals(other.playerDetails)) {
	      return false;
	    }
	    return true;
	  }
}
