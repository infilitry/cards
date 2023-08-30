package com.logicea.security.api.payload;

import java.time.LocalDateTime;
import java.util.Set;

import com.logicea.security.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
	
	private String email;
	private String password;
	private Set<String> role;

}
