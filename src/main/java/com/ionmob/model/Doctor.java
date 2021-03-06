package com.ionmob.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * This Class holds the Doctor data from the tb_doctor table
 * 
 * @author I Made Putrama
 *
 */
@Entity
@Table(name="tb_doctor")
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Integer id;

	@Getter
	@Setter
	private String firstname;

	@Getter
	@Setter
	private String lastname;

	@Getter
	@Setter
	@Column(name="create_dt", columnDefinition = "TIMESTAMP")
	private Date createDt;

	@Getter
	@Setter
	@Column(name="update_dt", columnDefinition = "TIMESTAMP")
	private Date updateDt;

	@Getter
	@Setter
	@OneToMany(mappedBy = "doctor")
    Set<Prescription> prescriptions;   
}
