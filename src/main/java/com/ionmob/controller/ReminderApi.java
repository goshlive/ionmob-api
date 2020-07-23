package com.ionmob.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ionmob.exception.RecordNotFoundException;
import com.ionmob.model.Prescription;
import com.ionmob.model.Reminder;
import com.ionmob.model.ReminderDetail;
import com.ionmob.repo.PrescriptionRepository;
import com.ionmob.repo.ReminderRepository;

/**
 * This Class provides API related to Reminder data in the database
 * 
 * @author I Made Putrama
 *
 */
@RestController
@ResponseBody
@CrossOrigin(origins = "*")
public class ReminderApi {

	@Autowired
	private PrescriptionRepository prescriptionRepository;
	@Autowired
	private ReminderRepository reminderRepository;
	
	@GetMapping("/api/reminders")
	public List<Reminder> getAllReminders() {
		return reminderRepository.findAll();
	}

	@GetMapping("/api/reminder/{id}")
	public Optional<Reminder> getReminderById(@PathVariable("id") int id) {
		return reminderRepository.findById(id);
	}
	
	@GetMapping("/api/reminder/{id}/detail")
	public Optional<ReminderDetail> getAllReminderDetailById(@PathVariable("id") int id) {
		return reminderRepository.findDetailById(id);
	}
		
	@GetMapping("/api/doctor/{id}/reminders")
	public List<Reminder> getAllRemindersByDoctor(@PathVariable("id") int id) {
		return reminderRepository.findByPrescriptionDoctorId(id);
	}
	
	@GetMapping("/api/patient/{id}/reminders")
	public List<Reminder> getAllRemindersByPatient(@PathVariable("id") int id) {
		return reminderRepository.findByPrescriptionPatientId(id);
	}
	
	@GetMapping("/api/patient/{id}/{ddMMyyyy}/reminders")
	public List<ReminderDetail> getAllReminderDetailsByPatientAndDate(@PathVariable("id") int id, @PathVariable("ddMMyyyy") String date) {
		return reminderRepository.findDetail(id, date);
	}
	
	@GetMapping("/api/doctor/{did}/patient/{pid}/reminders")
	public List<Reminder> getAllRemindersByDoctorAndPatient(@PathVariable("did") int did, @PathVariable("pid") int pid) {
		return reminderRepository.findByDoctorAndPatient(did, pid);
	}
	
	@GetMapping("/api/prescription/{id}/reminders")
	public List<Reminder> getAllRemindersByPrescription(@PathVariable("id") int id) {
		return reminderRepository.findByPrescriptionId(id);
	}
	
	@PostMapping("/api/post/reminder")
	public void postAReminder(@RequestBody Reminder reminder) {	
		Optional<Prescription> pOptional = prescriptionRepository.findById(reminder.getId());
		Prescription p = pOptional.get();
		reminder.setPrescription(p);
		reminderRepository.save(reminder);
	}
	
	@PostMapping("/api/prescription/{id}/reminders")
	public void postReminders(@PathVariable int id, @RequestBody ArrayList<Reminder> reminders) {
		Optional<Prescription> pOptional = prescriptionRepository.findById(id);
		if (!pOptional.isPresent()) {
			throw new RecordNotFoundException("Prescription with id: " + id + " not found.");
		}
		
		Prescription p = pOptional.get();
		Iterator<Reminder> it = reminders.iterator();	
		while (it.hasNext()) {
			Reminder r = it.next();
			r.setPrescription(p);
		}
		reminderRepository.saveAll(reminders);
	}
	
	@DeleteMapping("/api/reminder/{id}")
	public void delReminderById(@PathVariable int id) {
		reminderRepository.deleteById(id);
	}

	@PutMapping("/api/reminder/{id}/done")
	public void reminderDone(@PathVariable int id) {
		reminderRepository.setDone(id);
	}
	
	@DeleteMapping("/api/reminders")
	public @ResponseBody void delReminders() {
		reminderRepository.deleteAll();
	}
}
