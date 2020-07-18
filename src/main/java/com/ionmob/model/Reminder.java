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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_reminder")
public class Reminder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "prescription_id")
	@Getter
	@Setter
	private Prescription prescription;
 
    @Getter
	@Setter
	private String message;
	
    @Getter
	@Setter
	private String priority;
    
    @Getter
	@Setter
	private Float duration;

    @Getter
	@Setter
	private Float elapsed;

    @Getter
	@Setter
	@Column(name = "late_ind")
	private Integer lateInd;

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
