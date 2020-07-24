package com.ionmob.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ionmob.model.Graph;
import com.ionmob.model.Patient;
import com.ionmob.model.PatientDetail;
import com.ionmob.model.Prescription;
import com.ionmob.model.Reminder;
import com.ionmob.model.ReminderDetail;
import com.ionmob.model.Roles;
import com.ionmob.model.User;
import com.ionmob.repo.PatientRepository;
import com.ionmob.repo.PrescriptionRepository;
import com.ionmob.repo.ReminderRepository;
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
	private PrescriptionRepository prescriptionRepository;
	@Autowired
	private ReminderRepository reminderRepository;
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	UserRepository userRepository;	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/api/patient/{id}/reminders")
	public List<ReminderDetail> getRemindersByPatientId(@PathVariable("id") int id) {
		return reminderRepository.findDetail(id);
	}
	
	@GetMapping("/api/patient-details/doctor/{id}")
	public List<PatientDetail> findPatientDetails(@PathVariable("id") int id) {
		return patientRepository.findPatientDetails(id);
	}

	@GetMapping("/api/patient/{id}")
	public Optional<Patient> getPatientById(@PathVariable("id") int id) {
		return patientRepository.findById(id);
	}
	
	@GetMapping("/api/patients/doctor/{id}")
	public List<Patient> getAllPatientsByDoctorId(@PathVariable("id") int id) {
		return patientRepository.findDistinctByPrescriptions_DoctorId(id);
	}

	@GetMapping("/api/patient-graph/{id}")
	public Optional<Graph> getReminderGraphData(@PathVariable("id") int id) {
		return patientRepository.getReminderGraphData(id);
	}

	@PostMapping("/api/post/reminder")
	public void postAReminder(@RequestBody Reminder reminder) {	
		Optional<Prescription> pOptional = prescriptionRepository.findById(reminder.getId());
		Prescription p = pOptional.get();
		reminder.setPrescription(p);
		reminderRepository.save(reminder);
	}
		
	@GetMapping("/api/reminder/{id}/detail")
	public Optional<ReminderDetail> getAllReminderDetailById(@PathVariable("id") int id) {
		return reminderRepository.findDetailById(id);
	}
	
	@PutMapping("/api/reminder/{id}/done")
	public void reminderDone(@PathVariable int id) {
		reminderRepository.setDone(id);
	}	
	
	/************************************************
	 * BELOW ARE API(s) related to User login/logout 
	 ************************************************/
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
