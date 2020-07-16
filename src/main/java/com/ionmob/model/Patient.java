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
@Table(name = "tb_patient")
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Integer id;
	
	@Getter
	@Setter
	private String username;

	@Getter
	private String password;
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Getter
	@Setter
	private String firstname;

    @Getter
	@Setter
	@Column(name = "create_dt")
	private Date createDt;	
    
    @Getter
	@Setter
	@Column(name = "update_dt")
	private Date updateDt;	
    
	@OneToMany(mappedBy = "patient")
	@Getter
	@Setter
    Set<Reminder> reminders;
}

