package com.logicea.card.api.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logicea.card.domain.Card;
import com.logicea.card.domain.Status;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author infilitry
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardRequest {

	@NotEmpty(message = "Name is required")
	private String name;
	
	private String color;
	
	private String description;
	
	private Status status;
	
	private Long userId;
}
