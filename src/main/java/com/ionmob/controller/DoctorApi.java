package com.ionmob.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ionmob.model.Doctor;
import com.ionmob.repo.DoctorRepository;

/**
 * This Class provides API related to Doctor data in the database
 * 
 * @author I Made Putrama
 *
 */
@RestController
@ResponseBody
@CrossOrigin(origins = "*")
public class DoctorApi {

	@Autowired
	private DoctorRepository doctorRepository;
	
	@GetMapping("/api/doctors")
	public Iterable<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}

	@GetMapping("/api/doctor/{id}")
	public Optional<Doctor> getDoctorById(@PathVariable("id") int id) {
		return doctorRepository.findById(id);
	}
	
	@PostMapping("/api/doctors")
	public void postDoctors(@RequestBody ArrayList<Doctor> doctors) {
		doctorRepository.saveAll(doctors);
	}
	
	@DeleteMapping("/api/doctors")
	public @ResponseBody void delReminders() {
		doctorRepository.deleteAll();
	}

}
