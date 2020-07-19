package com.ionmob.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ionmob.model.Reminder;
import com.ionmob.model.ReminderDetail;

/**
 * JPA Repository signature of the tb_reminder table
 * 
 * @author I Made Putrama
 *
 */
public interface ReminderRepository extends JpaRepository<Reminder, Integer> {
	List<Reminder> findByPrescriptionId(int id);
	List<Reminder> findByPrescriptionDoctorId(int id);
	List<Reminder> findByPrescriptionPatientId(int id);

	/**
	 * Native SQL-query to retrieve Reminder based on Doctor ID and Patient ID
	 * 
	 * @param did
	 * @param pid
	 * @return
	 */
	@Query(value = "SELECT r.* FROM tb_reminder r INNER JOIN tb_prescription p ON r.prescription_id = p.id "
			+ "WHERE p.doctor_id = ?1 AND p.patient_id = ?2", nativeQuery = true)
	List<Reminder> findByDoctorAndPatient(int did, int pid);
	
	/**
	 * Native SQL-query to update Reminder (based on ID) as Done
	 * @param id
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE tb_reminder r SET r.done_dt = NOW(), "
			+ "r.elapsed = TIMESTAMPDIFF(MINUTE, r.create_dt, r.done_dt)/60, "
			+ "r.late_ind = IF(r.elapsed > r.duration, 2, 1), update_dt = NOW() "
			+ "WHERE r.id = ?1", nativeQuery = true)
    void setDone(int id);
	
	/**
	 * Correlates the Native SQL-query (named=Reminder.findDetail) as in the Reminder model 
	 * 
	 * @param id
	 * @param date
	 * @return
	 */
	@Query(nativeQuery = true)
	List<ReminderDetail> findDetail(int id, String date);
	
	/**
	 * Correlates the Native SQL-query (named=Reminder.findDetailById) as in the Reminder model
	 * @param id
	 * @return
	 */
	@Query(nativeQuery = true)
	Optional<ReminderDetail> findDetailById(int id);
	
}
