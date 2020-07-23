package com.ionmob;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.ionmob.model.Doctor;
import com.ionmob.model.Patient;
import com.ionmob.model.Prescription;
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
public class MockTest2 {

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
		
	
	@Test
	@Rollback(false)
	void mock2Test() throws InterruptedException {		
		
		Optional<User> user = userRepository.findByUsername("d1");
		Optional<Doctor> doctor = doctorRepository.findById(user.get().getOwnerId());
		List<Prescription> docPres = prescriptionRepository.findByDoctorId(doctor.get().getId());
		Iterator<Prescription> it = docPres.iterator();
		while (it.hasNext()) {
			Prescription pres = it.next();
			Patient patient = pres.getPatient();
			log.debug("Doctor ["+doctor.get().getFirstname()+"], Patient["+patient.getFirstname()+"]===========================");
			log.debug("High unfinished [" + patient.getHighUnfinished()+"]");
			log.debug("Midddle unfinished [" + patient.getMiddleUnfinished()+"]");
			log.debug("Low unfinished [" + patient.getLowUnfinished()+"]");
		}
	}
}