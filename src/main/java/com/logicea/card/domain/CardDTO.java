package com.logicea.card.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;

/**
 * 
 * @author infilitry
 *
 */
public record CardDTO(
		
		Long id,
		
		LocalDateTime datecreated,
		
		LocalDateTime datemodified,
		
		@NotEmpty(message = "Name is required")
		String name,
		
		String color,
		
		String description,
		
		String status,
		
		Long userId
		
		) {
	
	 static CardDTO from(Card card) {
	        return new CardDTO(
	        		card.getId(),
	        		card.getDatecreated(),
	        		card.getDatemodified(),
	        		card.getName(),
	        		card.getColor(),
	        		card.getDescription(),
	        		card.getStatus().name(),
	        		card.getUserId()
	        );
	    }

}
