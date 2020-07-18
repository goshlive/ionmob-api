package com.ionmob.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ionmob.model.Patient;
import com.ionmob.repo.PatientRepository;

@RestController
@ResponseBody
@CrossOrigin(origins = "*")
public class PatientApi {

	@Autowired
	private PatientRepository patientRepository;
	
	@GetMapping("/api/patients")
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@GetMapping("/api/patient/{id}")
	public Optional<Patient> getPatientById(@PathVariable("id") int id) {
		return patientRepository.findById(id);
	}
	
	@PostMapping("/api/patients")
	public void postPatients(@RequestBody ArrayList<Patient> patients) {
		patientRepository.saveAll(patients);
	}

}
