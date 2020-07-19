package com.ionmob.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ionmob.model.Patient;

/**
 * JPA Repository signature of the tb_patient table
 * 
 * @author I Made Putrama
 *
 */
public interface PatientRepository extends JpaRepository<Patient, Integer> {
	List<Patient> findDistinctByPrescriptions_DoctorId(int id);
}