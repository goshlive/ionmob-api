package com.ionmob.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * This class provides value holder for custom data retrieval related to Reminder records 
 * 
 * @author I Made Putrama
 *
 */
public class ReminderDetail {
	public ReminderDetail(Integer id, 
			Integer docId, 
			Integer presId, 
			String doctorName, 
			String prescription,
			String message, 
			String priority, 
			Float duration, 
			Float elapsed,
			Integer lateInd, 
			Date createDt, 
			Date doneDt
			) {
		this.id = id;
		this.docId = docId;
		this.presId = presId;
		this.doctorName = doctorName;
		this.prescription = prescription;
		this.message = message;
		this.priority = priority;
		this.duration = duration;
		this.elapsed = elapsed;
		this.lateInd = lateInd;
		this.createDt = createDt;
		this.doneDt = doneDt;
	}

	@Getter
	@Setter
	private Integer id;

	@Getter
	@Setter
	private Integer docId;

	@Getter
	@Setter
	private Integer presId;

	@Getter
	@Setter
	private String doctorName;

	@Getter
	@Setter
	private String prescription;

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
	private Integer lateInd;
	
	@Getter
	@Setter
	private Float elapsed;

	@Getter
	@Setter
	private Date createDt;

	@Getter
	@Setter
	private Date doneDt;

}
