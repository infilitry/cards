package com.logicea.security.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author infilitry
 *
 */
public record UserDTO(
		
		@Email
		String email,
		
		@NotEmpty
		@Size(min = 6, message = "password should have at least 6 characters")
		String password
		
		) {
	
	

}
