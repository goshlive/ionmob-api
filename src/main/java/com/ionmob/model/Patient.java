package com.ionmob.model;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

