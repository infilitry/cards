package com.logicea.security.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logicea.security.api.payload.JWTResponse;
import com.logicea.security.api.payload.MessageResponse;
import com.logicea.security.api.payload.UserInfoResponse;
import com.logicea.security.api.payload.UserLoginRequest;
import com.logicea.security.api.payload.UserRegistrationRequest;
import com.logicea.security.config.JwtUtils;
import com.logicea.security.domain.Role;
import com.logicea.security.domain.RoleService;
import com.logicea.security.domain.User;
import com.logicea.security.domain.UserDetailsImpl;
import com.logicea.security.domain.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Users", description = "User management APIs")
public class UserResource {

	@Autowired AuthenticationManager authenticationManager;

	@Autowired PasswordEncoder encoder;

	@Autowired JwtUtils jwtUtils;
	
	@Autowired RoleService roleService;
	
	@Autowired UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequest loginRequest) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtils.generateJwtToken(authentication);

		//ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		 return ResponseEntity.ok(new JWTResponse(jwt, 
                 userDetails.getId(),
                 userDetails.getEmail(), 
                 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest signUpRequest) {


		if (userService.existsByEmail(signUpRequest.getEmail().toLowerCase())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		
		for (String str : strRoles) {
			System.out.println("this is the outcome ...." + str);
		}
		
		
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleService.findByName("ROLE_USER");
					//.orElseThrow(() -> new RuntimeException("Error: Role is not found DDDDDDDD."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				System.out.println("this is the outcome in the switch...." + role);
				switch (role) {
				case "admin":
					Role adminRole = roleService.findByName("ROLE_ADMIN");
					//.orElseThrow(() -> new RuntimeException("Error: Role is not found DDDDTTTTT."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleService.findByName("ROLE_USER");
					//.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userService.createUser(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}


}
