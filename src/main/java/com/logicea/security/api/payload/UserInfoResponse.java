package com.logicea.security.api.payload;

import java.util.List;

public record UserInfoResponse(
		Long id,
		String email,
		List<String> roles
		
		) {}
