package com.logicea.exceptions;

/**
 * 
 * @author infilitry
 *
 */
public class CardNotFoundException extends  RuntimeException {
	
    private static final long serialVersionUID = 1L;

	public CardNotFoundException(Long id) {
        super(String.format("Card with id=%d not found", id));
    }

    public static CardNotFoundException of(Long id) {
        return new CardNotFoundException(id);
    }

}
