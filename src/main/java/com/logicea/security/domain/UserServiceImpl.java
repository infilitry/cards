package com.logicea.security.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("This is the username .... " + username);

		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));

		if (user != null) { System.out.println(" this is the username ...." + user.getEmail()); }
		return UserDetailsImpl.build(user);
	}

	@Override
	public Optional<User> findByEmail(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		user.setDatecreated(LocalDateTime.now());
		user = userRepository.save(user);
		return user;
	}



}
