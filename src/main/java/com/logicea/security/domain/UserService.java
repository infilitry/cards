package com.logicea.security.domain;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	Optional<User> findByEmail(String username);

	Boolean existsByEmail(String email);
	
	User createUser(User user);

}
