package com.ionmob.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ionmob.model.Prescription;

/**
 * JPA Repository signature of the tb_prescription table
 * 
 * @author I Made Putrama
 *
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
	List<Prescription> findByDoctorId(int id);
	List<Prescription> findByPatientId(int id);
	
	/**
	 * Native SQL-query to retrieve Prescription based on Doctor ID and Patient ID
	 * @param did
	 * @param pid
	 * @return
	 */
	@Query(value = "SELECT p.* FROM tb_prescription p WHERE p.doctor_id = ?1 AND p.patient_id = ?2", nativeQuery = true)
	List<Prescription> findByDoctorAndPatient(int did, int pid);
}