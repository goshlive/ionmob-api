package com.ionmob.controller;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.ionmob.exception.RecordNotFoundException;
import com.ionmob.model.Doctor;
import com.ionmob.model.Patient;
import com.ionmob.model.Prescription;
import com.ionmob.model.Reminder;
import com.ionmob.repo.DoctorRepository;
import com.ionmob.repo.PatientRepository;
import com.ionmob.repo.PrescriptionRepository;

@RestController
@ResponseBody
@CrossOrigin(origins = "*")
public class PrescriptionApi {

	@Autowired
	private PrescriptionRepository prescriptionRepository;
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private PatientRepository patientRepository;
	
	@GetMapping("/api/prescriptions")
	public Iterable<Prescription> getAllPrescriptions() {
		return prescriptionRepository.findAll();
	}

	@GetMapping("/api/prescription/{id}")
	public Optional<Prescription> getPrescriptionById(@PathVariable("id") int id) {
		return prescriptionRepository.findById(id);
	}

	@GetMapping("/api/doctor/{id}/prescriptions")
	public List<Prescription> getAllPrescriptionsByDoctor(@PathVariable("id") int id) {
		return prescriptionRepository.findByDoctorId(id);
	}

	@GetMapping("/api/patient/{id}/prescriptions")
	public List<Prescription> getAllPrescriptionsByPatient(@PathVariable("id") int id) {
		return prescriptionRepository.findByPatientId(id);
	}
	
	@GetMapping("/api/doctor/{did}/patient/{pid}/prescriptions")
	public List<Prescription> getAllPrescriptionsByDoctorAndPatient(@PathVariable("did") int did, @PathVariable("pid") int pid) {
		return prescriptionRepository.findByDoctorAndPatient(did, pid);
	}

	@PostMapping("/api/doctor/{did}/patient/{pid}/prescriptions")
	public void postPrescriptions(@PathVariable("did") int did, @PathVariable("pid") int pid, @RequestBody ArrayList<Prescription> prescriptions) {
		Optional<Doctor> dOptional = doctorRepository.findById(did);
		if (!dOptional.isPresent()) {
			throw new RecordNotFoundException("Doctor with id: " + did + " not found.");
		}
		Optional<Patient> pOptional = patientRepository.findById(pid);
		if (!pOptional.isPresent()) {
			throw new RecordNotFoundException("Patient with id: " + pid + " not found.");
		}
		
		Doctor d = dOptional.get();
		Patient p = pOptional.get();
		Iterator<Prescription> it = prescriptions.iterator();	
		while (it.hasNext()) {
			Prescription pr = it.next();
			Iterator<Reminder> rit = pr.getReminders().iterator();
			while(rit.hasNext()) {
				Reminder r = rit.next();
				pr.addToReminder(r);
			}
			pr.setDoctor(d);
			pr.setPatient(p);
		}
		prescriptionRepository.saveAll(prescriptions);
	}

}

