package com.ionmob.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ionmob.model.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Integer> {
	List<Reminder> findByPrescriptionId(int id);
	List<Reminder> findByPrescriptionDoctorId(int id);
	List<Reminder> findByPrescriptionPatientId(int id);

	@Query(value = "SELECT r.* FROM tb_reminder r INNER JOIN tb_prescription p ON r.prescription_id = p.id "
			+ "WHERE p.doctor_id = ?1 AND p.patient_id = ?2", nativeQuery = true)
	List<Reminder> findByDoctorAndPatient(int did, int pid);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE tb_reminder r SET r.done_dt = NOW(), "
			+ "r.elapsed = TIMESTAMPDIFF(MINUTE, r.create_dt, r.done_dt)/60, "
			+ "r.late_ind = IF(r.elapsed > r.duration, 1, 0), update_dt = NOW() "
			+ "WHERE r.id = ?1", nativeQuery = true)
    void setDone(int id);
}
