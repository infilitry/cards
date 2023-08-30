package com.logicea.exceptions;

public class UserNotFoundException  extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long id) {
        super(String.format("User with id=%d not found", id));
    }

    public static UserNotFoundException of(Long id) {
        return new UserNotFoundException(id);
    }

}
