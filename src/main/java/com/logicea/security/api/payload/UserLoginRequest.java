package com.logicea.security.api.payload;

/**
 * 
 * @author infilitry
 *
 */
public record UserLoginRequest(

		String email,
		String password

		) {

}
