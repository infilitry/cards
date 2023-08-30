package com.logicea.card.api.payload;

import com.logicea.card.domain.Status;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class UpdateCardRequest {
	
	private Long id;
	
	@NotEmpty(message = "Name is required")
	private String name;
	
	private String color;
	
	private String description;
	
	private Status status;
	
	private Long userId;

}
