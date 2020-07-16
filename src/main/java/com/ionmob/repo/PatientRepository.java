package com.ionmob.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ionmob.model.Patient;

public interface PatientRepository extends JpaRepository <Patient, Integer>{

}
