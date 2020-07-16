package com.ionmob.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ionmob.model.Doctor;
import com.ionmob.model.Patient;
import com.ionmob.model.Reminder;
import com.ionmob.repo.DoctorRepository;
import com.ionmob.repo.PatientRepository;
import com.ionmob.repo.ReminderRepository;

@RestController
@ResponseBody
@CrossOrigin(origins = "*")
public class ServiceController {
	
	@Autowired
	ReminderRepository reminderRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/reminders")
	public List<Reminder> getAllReminders(){
		return reminderRepository.findAll();
	}
	
	@GetMapping("/reminders/{id}")
	public Optional<Reminder> getRemindersById(@PathVariable("id") int id){
		return reminderRepository.findById(id);
	}
	
	@GetMapping("/reminders/doctor/{id}")
	public List<Reminder> getDoctorReminderById(@PathVariable("id") int id){
		return reminderRepository.findByDoctor_Id(id);
	}

	@GetMapping("/reminders/patient/{id}")
	public List<Reminder> getPatientReminderById(@PathVariable("id") int id){
		return reminderRepository.findByPatient_Id(id);
	}
	
	@PostMapping("/reminders/{did}/{pid}")
	public void insertReminder(@PathVariable int did, @PathVariable int pid, @RequestBody Reminder reminder){
		Optional<Doctor> doctorOptional= doctorRepository.findById(did);  
		if(!doctorOptional.isPresent())  
		{  
			throw new RuntimeException("Doctor with id: "+ did+" not found.");  
		}  
		Optional<Patient> patientOptional= patientRepository.findById(did);  
		if(!patientOptional.isPresent())  
		{  
			throw new RuntimeException("Patient with id: "+ did+" not found.");  
		}  
		
		Doctor doctor = doctorOptional.get();
		Patient patient = patientOptional.get();
		reminder.setDoctor(doctor);
		reminder.setPatient(patient);
		
		reminderRepository.save(reminder);
	}
	
	@PutMapping("/reminders/{id}/done")
	public void reminderDone(@PathVariable int id) {
		reminderRepository.setDone(id);
	}
	
	@PostMapping("/doctors")
	public void insertDoctor(@RequestBody Doctor doctor){
		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
		doctorRepository.save(doctor);
	}
	
	@PostMapping("/patients")
	public void insertPatient(@RequestBody Patient patient){
		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		patientRepository.save(patient);
	}	
}
