package com.ionmob.model;

import lombok.Getter;
import lombok.Setter;

public class PatientDetail {
	
	public PatientDetail(
			Integer docId, 
			String docName, 
			Integer patId, 
			String patName, 
			Integer unfinishedHigh,
			int unfinishedMiddle, 
			int unfinishedLow
			) {
		this.docId = docId;
		this.docName = docName;
		this.patId = patId;
		this.patName = patName;
		this.unfinishedHigh = unfinishedHigh;
		this.unfinishedMiddle = unfinishedMiddle;
		this.unfinishedLow = unfinishedLow;
	}
	
	@Getter
	@Setter
	private Integer docId;
	
	@Getter
	@Setter
	private String docName;

	@Getter
	@Setter
	private Integer patId;
	
	@Getter
	@Setter
	private String patName;

	@Getter
	@Setter
	private int unfinishedHigh;

	@Getter
	@Setter
	private int unfinishedMiddle;

	@Getter
	@Setter
	private int unfinishedLow;

}
