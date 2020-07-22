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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ionmob.model.Roles;
import com.ionmob.model.User;
import com.ionmob.repo.UserRepository;
import com.ionmob.service.UserDetailsImpl;

/**
 * This Class provides API related to User data in the database
 * 
 * @author I Made Putrama
 *
 */
@RestController
@ResponseBody
@CrossOrigin(origins = "*")
public class ServiceApi {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private ResponseEntity<User> returnOK(String p, Optional<User> oUser) {
		UserDetails userDetails = oUser.map(UserDetailsImpl::new).get();
		boolean pwMatches = passwordEncoder.matches(p, userDetails.getPassword());
		if (pwMatches) {
			return ResponseEntity.ok(oUser.get());
		}		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();		
	}

	@PostMapping("/api/doctor/login")
	public ResponseEntity<User> doctorLogin(@RequestBody User loginUser) {
		Optional<User> oUser = userRepository.findByUsername(loginUser.getUsername());
		if (oUser != null) {
			if (Roles.DOCTOR.name().equals(oUser.get().getRoles())) {
				return returnOK(loginUser.getPassword(), oUser);
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@PostMapping("/api/patient/login")
	public ResponseEntity<User> patientLogin(@RequestBody User loginUser) {
		Optional<User> oUser = userRepository.findByUsername(loginUser.getUsername());
		if (oUser != null) {
			if (Roles.PATIENT.name().equals(oUser.get().getRoles())) {
				return returnOK(loginUser.getPassword(), oUser);
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
	
	@DeleteMapping("/api/users")
	public @ResponseBody void delUsers() {
		userRepository.deleteAll();
	}

}
