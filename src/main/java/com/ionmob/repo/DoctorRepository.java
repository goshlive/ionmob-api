package com.ionmob.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ionmob.model.Doctor;

/**
 * JPA Repository signature of the tb_doctor table
 * 
 * @author I Made Putrama
 *
 */
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {}