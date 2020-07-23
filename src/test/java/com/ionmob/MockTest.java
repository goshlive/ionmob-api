package com.ionmob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.ionmob.model.Doctor;
import com.ionmob.model.Patient;
import com.ionmob.model.Prescription;
import com.ionmob.model.Priority;
import com.ionmob.model.Reminder;
import com.ionmob.model.Roles;
import com.ionmob.model.User;
import com.ionmob.repo.DoctorRepository;
import com.ionmob.repo.PatientRepository;
import com.ionmob.repo.PrescriptionRepository;
import com.ionmob.repo.ReminderRepository;
import com.ionmob.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class MockTest {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private ReminderRepository reminderRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private void cleanDB(boolean data, boolean user) {
		if (data) {
			//remove all reminders in DB
			reminderRepository.deleteAll();		
			//remove all prescriptions in DB
			prescriptionRepository.deleteAll();
		}
		if (user) {
			//remove all doctors in DB
			doctorRepository.deleteAll();
			//remove all patients in DB
			patientRepository.deleteAll();
			//remove all users in DB
			userRepository.deleteAll();
		}
	}
	
	void createMultiPatients(int total) {
		ArrayList<Patient> patients = new ArrayList<Patient>();
		for (int i=0; i<total; i++) {
			Patient patient = new Patient();
			patient.setFirstname("p00"+(i+1)+"_first");
			patient.setLastname("p00"+(i+1)+"_last");
			patients.add(patient);
		}
		patientRepository.saveAll(patients);
	}
	
	User getUser(Roles roles, Integer ownerId, int counter) {
		User user = new User();
		if (Roles.DOCTOR.equals(roles)) user.setUsername("doctor"+counter);
		if (Roles.PATIENT.equals(roles)) user.setUsername("patient"+counter);
		if (Roles.ADMIN.equals(roles)) user.setUsername("admin"+counter);
		user.setPassword(passwordEncoder.encode("1"));
		if (Roles.DOCTOR.equals(roles)) user.setEmail("doctor"+counter+"@test.com");
		if (Roles.PATIENT.equals(roles)) user.setEmail("patient"+counter+"@test.com");
		if (Roles.ADMIN.equals(roles)) user.setEmail("admin"+counter+"@test.com");
		user.setRoles(roles.name());
		user.setOwnerId(ownerId);
		return user;
	}
	
	void createMultiPatientUsers() {
		List<Patient> patients = patientRepository.findAll();
		Iterator<Patient> it = patients.iterator();
		int i=0;
		while (it.hasNext()) {
			Patient patient = it.next();
			User user = getUser(Roles.PATIENT, patient.getId(),++i);
			userRepository.save(user);
			
			//just create one
			break;
		}
	}
	
	void createMultiDoctorUsers() {
		List<Doctor> doctors = doctorRepository.findAll();
		Iterator<Doctor> it = doctors.iterator();
		int i=0;
		while (it.hasNext()) {
			Doctor doctor = it.next();
			User user = getUser(Roles.DOCTOR, doctor.getId(),++i);
			userRepository.save(user);

			//just create one
			break;
		}
	}
	
	void createMultiDoctors(int total) {
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		for (int i=0; i<total; i++) {
			Doctor doctor = new Doctor();
			doctor.setFirstname("d00"+(i+1)+"_first");
			doctor.setLastname("d00"+(i+1)+"_last");
			doctors.add(doctor);
		}
		doctorRepository.saveAll(doctors);
	}
	
	void createPrescription(Integer docId, Integer patId) {
		Prescription newPres = new Prescription();
		newPres.setPrescription("This is a sample prescription from Doctor["+docId+"] given to Patient["+patId+"] to be taken regularly as stated.");
		Optional<Doctor> doctor = doctorRepository.findById(docId);
		Optional<Patient> patient = patientRepository.findById(patId);
		newPres.setDoctor(doctor.get());
		newPres.setPatient(patient.get());
		prescriptionRepository.save(newPres);
	}
	

	static Random rand = new Random();
	private String getRandom() {
		List<Priority> prs = Arrays.asList(Priority.values());
        return prs.get(rand.nextInt(prs.size())).name(); 
	}
	
	private void randomTime(Calendar cal, int offset) {
		int addHour = rand.nextInt(offset)-offset;
		long millis = cal.getTimeInMillis() + (addHour*60*60*1000);
		cal.setTimeInMillis(millis);
	}
	
	float computeElapsed(Calendar cal, Calendar cal2) {		
		long diff = cal2.getTimeInMillis() - cal.getTimeInMillis();
		float diffHours = diff / (60 * 60 * 1000); 
		return diffHours;
	}
	
	void setDone(Reminder reminder, Calendar cal) {		
		Calendar cal2 = Calendar.getInstance(); 
		randomTime(cal2, 10);

		Calendar temp = null;
		if (cal2.before(cal)) {
			temp = cal2;
			cal2 = cal;
			cal = temp;
			reminder.setCreateDt(cal.getTime());
		}
		
		Date doneDt = cal2.getTime();
		reminder.setDoneDt(doneDt);
		reminder.setUpdateDt(doneDt);
		
		float elapsed = computeElapsed(cal, cal2);
		reminder.setLateInd(1);
		if (elapsed > reminder.getDuration()) reminder.setLateInd(2);
		reminder.setElapsed(elapsed);
	}
	
	void createReminder(int counter, Prescription pres, int loop, Calendar cal, String doctorName) throws InterruptedException {
		List<Reminder> reminders = new ArrayList<Reminder>();
		for (int i=0; i<loop; i++) {
			Reminder reminder = new Reminder();
			reminder.setMessage("This is a the reminder no.["+(i+1)+"] from ["+doctorName+"]: please take the medicine on time.");
			reminder.setDuration((float)rand.nextInt(9)+1);
			reminder.setPriority(getRandom());
			
			randomTime(cal, 10);
			Date createDt = cal.getTime();
			reminder.setCreateDt(createDt);			
			setDone(reminder, cal);
			
			reminder.setPrescription(pres);
			reminders.add(reminder);
		}
		reminderRepository.saveAll(reminders);
	}
	
	@Test
	@Rollback(false)
	void mock1Test() throws InterruptedException {	
		log.debug("Starting mock...");
		//cleanup
		cleanDB(true, true);
		
		//1. create 20,000 patients
		int patients = 20000;
		createMultiPatients(patients);
		createMultiPatientUsers();
		
		//2. create 500 doctors
		int doctors = 500;
		createMultiDoctors(doctors);
		createMultiDoctorUsers();
		
		//3. assign each doctor 40 patients
		//3.1 first get all doctors and patients
		List<Doctor> docRecords = doctorRepository.findAll();
		List<Patient> patRecords = patientRepository.findAll();
		
        //3.1 assign 40 patients to each of the 500 doctors
        int assign = 40;
		for (int i=0; i<doctors; i++) {
			
			//3.1.1 take one doctor each time
			Doctor doctor = docRecords.get(i);
			
			//initial selection of the patient depends on the currently selected doctor = doctor_th * 40
			for (int j=(i*assign); j<patients; j++) {
				//3.1.1.1 take the first 40 patients for the selected doctor
				Patient patient = patRecords.get(j);
				
				//post prescription				
				createPrescription(doctor.getId(), patient.getId());
				
				//we already assign 40 patients, now exit from this loop
				if ((j+1) % assign == 0) break;
			}			
		}
		
		//4. pick 1 doctor, generate 20 reminders for all his patient for the last 15 days
		int reminders = 20;
		int days = 15;
		Optional<User> user = userRepository.findByUsername("doctor1");
		Optional<Doctor> doctor = doctorRepository.findById(user.get().getOwnerId());
		List<Prescription> docPres = prescriptionRepository.findByDoctorId(doctor.get().getId());
		Iterator<Prescription> it = docPres.iterator();
		while (it.hasNext()) {
			Prescription pres = it.next();
			for (int i=0; i<days; i++) {
				Calendar reminderCal = Calendar.getInstance();
				int date = reminderCal.get(Calendar.DATE) - i;		
				reminderCal.set(Calendar.DATE, date);
				createReminder(i, pres, reminders, reminderCal, doctor.get().getFirstname());
			}
		}
		log.debug("Starting mock. DONE.");
	}
}