package com.logicea.dataloader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.logicea.security.domain.Role;
import com.logicea.security.domain.RoleRepository;
import com.logicea.security.domain.RoleService;
import com.logicea.security.domain.User;
import com.logicea.security.domain.UserRepository;
import com.logicea.security.domain.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
	
	@Autowired private PasswordEncoder encoder;
	@Autowired private UserRepository userRepository;
	@Autowired private RoleRepository roleRepository;
	
	@PostConstruct
	public void createTestUsers() {
		
		User user = new User();
		user.setDatecreated(LocalDateTime.now());
		user.setEmail("testing1@test.com");
		user.setPassword(encoder.encode("12345678"));
		
		Set<Role> roles = new HashSet();
		roles.add(createRole("ROLE_ADMIN"));
		roles.add(createRole("ROLE_USER"));
		user.setRoles(roles);
		
		user = userRepository.save(user); 
		
		user = new User();
		user.setDatecreated(LocalDateTime.now());
		user.setEmail("testing2@test.com");
		user.setPassword(encoder.encode("12345678"));
		
		roles = new HashSet();
		roles.add(createRole("ROLE_USER"));
		user.setRoles(roles);
		
	}
	
	
	private Role createRole(String role) {
		
		Role rl = roleRepository.findByName(role);
		
		if (rl != null) {
			return rl;
		}
		
		rl = new Role();
		rl.setDatecreated(LocalDateTime.now());
		rl.setName(role);
		rl = roleRepository.save(rl);
		return rl;
	}
	

}
