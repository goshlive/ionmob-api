package com.ionmob.exception;

/**
 * This Class provides API custom exception
 * 
 * @author I Made Putrama
 *
 */
public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -357230633950669465L;

	/**
	 * Constructs a <code>UsernameNotFoundException</code> with the specified message.
	 *
	 * @param msg the detail message.
	 */
	public RecordNotFoundException(String msg) {
		super(msg);
	}
}
