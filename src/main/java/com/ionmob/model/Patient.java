package com.ionmob.model;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * This Class holds the Patient data from the tb_patient table
 * 
 * @author I Made Putrama
 *
 */

@SqlResultSetMapping(name = "patientDetailMapping", classes = {
		@ConstructorResult(targetClass = PatientDetail.class, 
				columns = {
					@ColumnResult(name = "docId", type=Integer.class),
					@ColumnResult(name = "docName", type=String.class), 
					@ColumnResult(name = "patId", type=Integer.class), 
					@ColumnResult(name = "patName", type=String.class),
					@ColumnResult(name = "unfinishedHigh", type=int.class),
					@ColumnResult(name = "unfinishedMiddle", type=int.class), 
					@ColumnResult(name = "unfinishedLow", type=int.class)
				})
		})

@NamedNativeQuery(name = "Patient.findPatientDetails", query = "select sub.docId, sub.docName, sub.patId, sub.patName, " + 
		"sum(case when sub.priority = 'HIGH' then 1 else 0 end) as unfinishedHigh, " + 
		"sum(case when sub.priority = 'MIDDLE' then 1 else 0 end) as unfinishedMiddle, " + 
		"sum(case when sub.priority = 'LOW' then 1 else 0 end) as unfinishedLow " + 
		"from ( select a.docId, a.docName, a.patId, a.patName, r.id as 'remId', r.priority  " + 
		"	from ( SELECT DISTINCT pres.id as 'presId', pres.prescription, pres.doctor_id as 'docId', pres.patient_id as 'patId', " + 
		"    concat(d.firstname, d.lastname) as 'docName', " + 
		"    concat(p.firstname, p.lastname) as 'patName' " + 
		"    from tb_prescription pres " + 
		"    inner join tb_patient p on pres.patient_id = p.id " + 
		"    inner join tb_doctor d on pres.doctor_id = d.id " + 
		"    where pres.doctor_id = ?1 ) a " + 
		"left join tb_reminder r on a.presId = r.prescription_id and r.done_dt is null " + 
		") sub group by sub.docId, sub.patId", resultSetMapping = "patientDetailMapping")

@SqlResultSetMapping(name = "graphMapping", classes = {
		@ConstructorResult(targetClass = Graph.class, 
				columns = {
					@ColumnResult(name = "docId", type=Integer.class),
					@ColumnResult(name = "docName", type=String.class), 
					@ColumnResult(name = "patId", type=Integer.class), 
					@ColumnResult(name = "patName", type=String.class),
					@ColumnResult(name = "createDt", type=Date.class),
					@ColumnResult(name = "unfinished0", type=int.class),
					@ColumnResult(name = "unfinished1", type=int.class), 
					@ColumnResult(name = "unfinished2", type=int.class), 
					@ColumnResult(name = "unfinished3", type=int.class), 
					@ColumnResult(name = "unfinished4", type=int.class), 
					@ColumnResult(name = "unfinished5", type=int.class), 
					@ColumnResult(name = "unfinished6", type=int.class)
				})
		})

@NamedNativeQuery(name = "Patient.getReminderGraphData", query = "select sub.docId, sub.docName, sub.patId, sub.patName, sub.createDt, " + 
		"sum(case when (TIMESTAMPDIFF(DAY, sub.createDt, now()) = 0) then 1 else 0 end) as unfinished0, " + 
		"sum(case when (TIMESTAMPDIFF(DAY, sub.createDt, now()) = 1) then 1 else 0 end) as unfinished1, " + 
		"sum(case when (TIMESTAMPDIFF(DAY, sub.createDt, now()) = 2) then 1 else 0 end) as unfinished2, " + 
		"sum(case when (TIMESTAMPDIFF(DAY, sub.createDt, now()) = 3) then 1 else 0 end) as unfinished3, " + 
		"sum(case when (TIMESTAMPDIFF(DAY, sub.createDt, now()) = 4) then 1 else 0 end) as unfinished4, " + 
		"sum(case when (TIMESTAMPDIFF(DAY, sub.createDt, now()) = 5) then 1 else 0 end) as unfinished5, " + 
		"sum(case when (TIMESTAMPDIFF(DAY, sub.createDt, now()) = 6) then 1 else 0 end) as unfinished6, " + 
		"count(sub.remId) as totalUnfinised " + 
		"from ( select a.docId, a.docName, a.patId, a.patName, r.id as 'remId', r.priority, r.create_dt as 'createDt' " + 
		"	from ( SELECT DISTINCT pres.id as 'presId', pres.prescription, pres.doctor_id as 'docId', pres.patient_id as 'patId', " + 
		"    concat(d.firstname, d.lastname) as 'docName', " + 
		"    concat(p.firstname, p.lastname) as 'patName' " + 
		"    from tb_prescription pres " + 
		"    inner join tb_patient p on pres.patient_id = p.id " +
		"    inner join tb_doctor d on pres.doctor_id = d.id " + 
		"    where pres.patient_id = ?1) a " + 
		"left join tb_reminder r on a.presId = r.prescription_id and r.done_dt is null and (TIMESTAMPDIFF(DAY, r.create_dt, now()) <= 7) " + 
		") sub group by sub.docId, sub.patId;", resultSetMapping = "graphMapping")

@Entity
@Table(name="tb_patient")
@Slf4j
public class Patient {
	
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
	@OneToMany(mappedBy = "patient")
    Set<Prescription> prescriptions;
	
	@Transient
	private boolean counted;
	@Transient
	private int highUnf;
	@Transient
	private int middleUnf;
	@Transient
	private int lowUnf;
	
	
	@Transient
	public String getName() {
		return getFirstname() + " " + getLastname();
	}
	
	public int getHighUnfinished() {
		if (!counted) hitCount();
		return highUnf;
	}
	
	public int getMiddleUnfinished() {
		return middleUnf;
	}

	public int getLowUnfinished() {
		return lowUnf;
	}
	
	/**
	 * Iterates through the prescriptions and check for Late or Not-done reminders
	 */
	private void hitCount() {
		if (getPrescriptions() != null) {
			Iterator<Prescription> itp = getPrescriptions().iterator();
			while(itp.hasNext()) {
				Prescription pres = itp.next();
				Iterator<Reminder> itr = pres.getReminders().iterator();
				while (itr.hasNext()) {
					Reminder rem = itr.next();
					if (rem.getDoneDt() != null) {
						log.debug("TASK DONE=============================================lateind["+rem.getLateInd()+"]");
						if (1 == rem.getLateInd()) {
							//ON TIME
							continue;
						} else if (2 == rem.getLateInd()) {
							//OVERDUE
							counter(rem);
							continue;
						}
					} else {
						log.debug("TASK NOT DONE=============================================");
						//NOT DONE, check if it is OVERDUE
						long diffInMillies = Math.abs(new Date().getTime() - getCreateDt().getTime());
					    long diffInMins = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
					    float diffInHours = diffInMins / 60;
					    log.debug("TASK NOT DONE diff["+ diffInHours +"] hours, duration["+rem.getDuration()+"]");
						if (diffInHours > rem.getDuration()) {
							//DURATION has passed, count it
							counter(rem);
							continue;
						}
					}
				}
			}
		}
	}	


	/**
	 * Performs counting for HIGH, MIDDLE and LOW reminders
	 * @param rem
	 */
	private void counter(Reminder rem) {
		highUnf += rem.getPriority().equals(Priority.HIGH.name()) ? 1 : 0;
		middleUnf += rem.getPriority().equals(Priority.MIDDLE.name()) ? 1 : 0;
		lowUnf += rem.getPriority().equals(Priority.LOW.name()) ? 1 : 0;
	}
	
	
}

