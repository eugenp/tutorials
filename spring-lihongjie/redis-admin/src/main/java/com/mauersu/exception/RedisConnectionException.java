package com.mauersu.exception;

public class RedisConnectionException extends RuntimeException {

	public RedisConnectionException(String errormsg) {
		super(errormsg);
	}

}
