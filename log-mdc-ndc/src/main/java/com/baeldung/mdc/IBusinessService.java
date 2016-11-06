package com.baeldung.mdc;

public interface IBusinessService {

	/** Sample service transferring a given amount of money. 
	 * @return {@code true} when the transfer complete successfully, {@code false} otherwise. */
	boolean transfer(long amount);

}
