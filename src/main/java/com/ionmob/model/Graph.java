package com.ionmob.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Graph {	
	
	public Graph(Integer docId, String docName, Integer patId, String patName, Date createDt, int unfinished0, int unfinished1,
			int unfinished2, int unfinished3, int unfinished4, int unfinished5, int unfinished6) {
		super();
		this.docId = docId;
		this.docName = docName;
		this.patId = patId;
		this.patName = patName;
		this.createDt = createDt;
		this.unfinished0 = unfinished0;
		this.unfinished1 = unfinished1;
		this.unfinished2 = unfinished2;
		this.unfinished3 = unfinished3;
		this.unfinished4 = unfinished4;
		this.unfinished5 = unfinished5;
		this.unfinished6 = unfinished6;
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
	private Date createDt;

	@Getter
	@Setter
	private int unfinished0;	

	@Getter
	@Setter
	private int unfinished1;	

	@Getter
	@Setter
	private int unfinished2;	

	@Getter
	@Setter
	private int unfinished3;	

	@Getter
	@Setter
	private int unfinished4;	

	@Getter
	@Setter
	private int unfinished5;	

	@Getter
	@Setter
	private int unfinished6;	

}
