package com.ionmob.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.ionmob.model.Graph;
import com.ionmob.model.Patient;
import com.ionmob.model.PatientDetail;
import com.ionmob.repo.PatientRepository;

/**
 * This Class provides API related to Patient data in the database
 * 
 * @author I Made Putrama
 *
 */
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
	
	@GetMapping("/api/patients/doctor/{id}")
	public List<Patient> getAllPatientsByDoctorId(@PathVariable("id") int id) {
		return patientRepository.findDistinctByPrescriptions_DoctorId(id);
	}

	@GetMapping("/api/patient-details/doctor/{id}")
	public List<PatientDetail> findPatientDetails(@PathVariable("id") int id) {
		return patientRepository.findPatientDetails(id);
	}

	@GetMapping("/api/patient-graph/{id}")
	public Optional<Graph> getReminderGraphData(@PathVariable("id") int id) {
		return patientRepository.getReminderGraphData(id);
	}

	@GetMapping("/api/patient/{id}")
	public Optional<Patient> getPatientById(@PathVariable("id") int id) {
		return patientRepository.findById(id);
	}
	
	@PostMapping("/api/patients")
	public void postPatients(@RequestBody ArrayList<Patient> patients) {
		patientRepository.saveAll(patients);
	}
	
	@DeleteMapping("/api/patients")
	public @ResponseBody void delPatients() {
		patientRepository.deleteAll();
	}

}
