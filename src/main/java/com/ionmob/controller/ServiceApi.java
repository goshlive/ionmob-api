package com.ionmob.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ionmob.model.User;
import com.ionmob.repo.UserRepository;
import com.ionmob.service.UserDetailsImpl;

@RestController
@ResponseBody
@CrossOrigin(origins = "*")
public class ServiceApi {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/api/login")
	public ResponseEntity<String> login(@RequestBody User loginUser) {
		Optional<User> user = userRepository.findByUsername(loginUser.getUsername());
		if (user != null) {
			UserDetails userDetails = user.map(UserDetailsImpl::new).get();
			boolean pwMatches = passwordEncoder.matches(loginUser.getPassword(), userDetails.getPassword());
			if (pwMatches) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).build();
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@PostMapping("/api/users")
	public void postUsers(@RequestBody ArrayList<User> users) {
		Iterator<User> it = users.iterator();
		while(it.hasNext()) {
			User user = it.next();
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		userRepository.saveAll(users);
	}

}
