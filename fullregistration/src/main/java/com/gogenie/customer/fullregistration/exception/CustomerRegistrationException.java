package com.gogenie.customer.fullregistration.exception;

public class CustomerRegistrationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4074951255773438898L;

	public CustomerRegistrationException() {
		super();
	}
	
	public CustomerRegistrationException(String message) {
		super(message);
	}
	
	public CustomerRegistrationException(Throwable t, String methodName) {
		super(t);
	}
	
	public CustomerRegistrationException(Exception e, String methodName) {
		super(e);
	}

	public CustomerRegistrationException(Throwable t) {
		super(t);
	}

	public CustomerRegistrationException(Exception e) {
		super(e);
	}

}
