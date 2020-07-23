package com.ionmob.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ionmob.model.Graph;
import com.ionmob.model.Patient;
import com.ionmob.model.PatientDetail;

/**
 * JPA Repository signature of the tb_patient table
 * 
 * @author I Made Putrama
 *
 */
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	List<Patient> findDistinctByPrescriptions_DoctorId(int id);
	
	/**
	 * Correlates the Native SQL-query (named=Patient.findPatientDetails) as in the Patient model 
	 * 
	 * @param id
	 * @return
	 */
	@Query(nativeQuery = true)
	List<PatientDetail> findPatientDetails(int id);
	
	/**
	 * Correlates the Native SQL-query (named=Patient.findPatientDetails) as in the Patient model 
	 * 
	 * @param id
	 * @return
	 */
	@Query(nativeQuery = true)
	Optional<Graph> getReminderGraphData(int id);	
}