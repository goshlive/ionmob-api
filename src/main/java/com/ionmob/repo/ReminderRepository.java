package com.ionmob.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ionmob.model.Reminder;

public interface ReminderRepository extends JpaRepository <Reminder, Integer>{
	List<Reminder> findByDoctor_Id(int doctorId);
	List<Reminder> findByPatient_Id(int patientId);
		
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Reminder r set r.doneDt = now() WHERE r.id = :id")
    void setDone(@Param("id") int id);
}
