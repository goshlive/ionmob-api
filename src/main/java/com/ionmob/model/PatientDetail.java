package com.ionmob.model;

import lombok.Getter;
import lombok.Setter;

public class PatientDetail {
	
	public PatientDetail(Integer id, String name, Integer presId, String prescription, Integer docId, String docName,
			Integer unfinishedHigh, Integer unfinishedMiddle, Integer unfinishedLow) {
		this.id = id;
		this.name = name;
		this.presId = presId;
		this.prescription = prescription;
		this.docId = docId;
		this.docName = docName;
		this.unfinishedHigh = unfinishedHigh;
		this.unfinishedMiddle = unfinishedMiddle;
		this.unfinishedLow = unfinishedLow;
	}

	@Getter
	@Setter
	private Integer id;
	
	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private Integer presId;
	
	@Getter
	@Setter
	private String prescription;

	@Getter
	@Setter
	private Integer docId;
	
	@Getter
	@Setter
	private String docName;

	@Getter
	@Setter
	private Integer unfinishedHigh;

	@Getter
	@Setter
	private Integer unfinishedMiddle;

	@Getter
	@Setter
	private Integer unfinishedLow;

}
