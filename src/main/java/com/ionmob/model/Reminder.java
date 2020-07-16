package com.ionmob.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_reminder")
public class Reminder {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
	
	@ManyToOne
    @JoinColumn(name = "id_doctor")
	@Getter
	@Setter
    Doctor doctor;
 
    @ManyToOne
    @JoinColumn(name = "id_patient")
    @Getter
	@Setter
    Patient patient;
	
    @Getter
	@Setter
	private String description;
	
    @Getter
	@Setter
	private String priority;
    
    @Getter
	@Setter
	private int duration;

    @Getter
	@Setter
	@Column(name = "done_dt")
	private Date doneDt;	
	
    @Getter
	@Setter
	@Column(name = "create_dt")
	private Date createDt;	
    
    @Getter
	@Setter
	@Column(name = "update_dt")
	private Date updateDt;	
    
}
