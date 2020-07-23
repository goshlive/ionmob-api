package com.ionmob;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.ionmob.model.PatientDetail;
import com.ionmob.repo.PatientRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MockTest3 {
	@Autowired
	private PatientRepository patientRepository;
	
	@Test
	@Rollback(false)
	void test() {
		List<PatientDetail> patDetails = patientRepository.findPatientDetails(1162);
		Iterator<PatientDetail> it = patDetails.iterator();
		
		while(it.hasNext()) {
			PatientDetail pat = it.next();
			System.out.println("docId["+pat.getDocId()+"], docName["+pat.getDocName()+"], "
					+ "patId["+pat.getPatId()+"], patName["+pat.getPatName()+"], "
					+ "unfin-HIGH["+pat.getUnfinishedHigh()+"], unfin-MIDDLE["+pat.getUnfinishedMiddle()+"], unfin-LOW["+pat.getUnfinishedLow()+"]");
		}
		
		patientRepository.getReminderGraphData(121447);
		
	}
}
