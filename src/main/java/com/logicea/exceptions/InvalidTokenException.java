package com.logicea.exceptions;

public class InvalidTokenException  extends  RuntimeException {
	
    private static final long serialVersionUID = 1L;

	public InvalidTokenException(String token) {
        super(String.format("Token of value value=%d not found", token));
    }

    public static InvalidTokenException of(String token) {
        return new InvalidTokenException(token);
    }

}
