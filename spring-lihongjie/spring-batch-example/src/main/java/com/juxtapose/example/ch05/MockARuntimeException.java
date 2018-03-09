/**
 * 
 */
package com.juxtapose.example.ch05;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-25下午10:28:35
 */
public class MockARuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3841087497940611523L;
	public MockARuntimeException(){
		super();
	}
	public MockARuntimeException(String message){
		super(message);
	}
}
