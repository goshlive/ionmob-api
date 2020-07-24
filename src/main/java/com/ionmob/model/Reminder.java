package com.ionmob.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Defines the mapping result-set of the native-SQL queries
 */
@SqlResultSetMapping(name = "getDetailMapping", classes = {
		@ConstructorResult(targetClass = ReminderDetail.class, 
				columns = { 
				@ColumnResult(name = "id", type=Integer.class),
				@ColumnResult(name = "doc_id", type=Integer.class),
				@ColumnResult(name = "pres_id", type=Integer.class),
				@ColumnResult(name = "doctor_name", type=String.class),
				@ColumnResult(name = "prescription", type=String.class),
				@ColumnResult(name = "message", type=String.class),
				@ColumnResult(name = "priority", type=String.class),
				@ColumnResult(name = "duration", type=Float.class),
				@ColumnResult(name = "elapsed", type=Float.class),
				@ColumnResult(name = "late_ind", type=Integer.class),
				@ColumnResult(name = "create_dt", type=Date.class),
				@ColumnResult(name = "done_dt",  type=Date.class),
				}) })

/**
 * Defines a native-SQL query to search Reminder detail records based on Patient ID and a specific Date
 */
@NamedNativeQuery(name = "Reminder.findDetail", query = "SELECT r.id, doc.id as doc_id, r.prescription_id as pres_id, "
		+ "CONCAT(doc.firstname, doc.lastname) as doctor_name, p.prescription, r.message, r.priority, "
		+ "r.duration, r.elapsed, r.late_ind, r.create_dt, r.done_dt "
		+ "FROM tb_reminder r INNER JOIN tb_prescription p "
		+ "ON r.prescription_id = p.id INNER JOIN tb_patient pat ON p.patient_id = pat.id "
		+ "INNER JOIN tb_doctor doc ON p.doctor_id = doc.id "
		+ "WHERE pat.id = ?1 "
		+ "order by r.create_dt desc", 
		resultSetMapping = "getDetailMapping")

/**
 * Defines a native-SQL query to search Reminder detail records based on ID
 */
@NamedNativeQuery(name = "Reminder.findDetailById", query = "SELECT r.id, doc.id as doc_id, r.prescription_id as pres_id, "
		+ "CONCAT(doc.firstname, doc.lastname) as doctor_name, p.prescription, r.message, r.priority, "
		+ "r.duration, r.elapsed, r.late_ind, r.create_dt, r.done_dt "
		+ "FROM tb_reminder r INNER JOIN tb_prescription p "
		+ "ON r.prescription_id = p.id INNER JOIN tb_patient pat ON p.patient_id = pat.id "
		+ "INNER JOIN tb_doctor doc ON p.doctor_id = doc.id WHERE r.id = ?1", 
		resultSetMapping = "getDetailMapping")

/**
 * This Class holds the Reminder data from the tb_reminder table
 * 
 * @author I Made Putrama
 *
 */
@Entity
@Table(name = "tb_reminder")
public class Reminder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
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
	@Column(name = "done_dt", columnDefinition = "TIMESTAMP")
	private Date doneDt;

	@Getter
	@Setter
	@Column(name = "create_dt", columnDefinition = "TIMESTAMP")
	private Date createDt;

	@Getter
	@Setter
	@Column(name = "update_dt", columnDefinition = "TIMESTAMP")
	private Date updateDt;
}
