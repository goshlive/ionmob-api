package com.ionmob.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2568871996119343366L;

	private int id;

	public MemberNotFoundException(int id) {
		super(String.format(" not found : '%i'", id));
		this.id=id;	
	}

	public int getId() {
		return this.id;
	}
}
