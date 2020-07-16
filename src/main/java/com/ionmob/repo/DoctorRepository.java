package com.ionmob.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ionmob.model.Doctor;

public interface DoctorRepository extends JpaRepository <Doctor, Integer>{

}
