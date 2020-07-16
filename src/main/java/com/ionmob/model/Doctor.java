package com.ionmob.model;

import java.sql.Date;
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

@Entity
@Table(name = "tb_doctor")
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Integer id;
	
	@Getter
	@Setter
	private String username;

	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	private String firstname;

	@Getter
	@Setter
	private String lastname;

    @Getter
	@Setter
	@Column(name = "create_dt")
	private Date createDt;	
    
    @Getter
	@Setter
	@Column(name = "update_dt")
	private Date updateDt;	
    
	@OneToMany(mappedBy = "doctor")
	@Getter
	@Setter
    Set<Reminder> reminders;
}

