package com.ionmob.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * This Class holds the Prescription data from the tb_prescription table
 * 
 * @author I Made Putrama
 *
 */
@Entity
@Table(name = "tb_prescription")
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Integer id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "doctor_id")
	@Getter
	@Setter
	Doctor doctor;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "patient_id")
	@Getter
	@Setter
	Patient patient;

	@Getter
	@Setter
	private String prescription;
	
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
	@OneToMany(mappedBy = "prescription",  cascade = CascadeType.ALL)
    Set<Reminder> reminders;

	public void addToReminder(Reminder reminder) {
		reminder.setPrescription(this);
		this.reminders.add(reminder);
	}

}
