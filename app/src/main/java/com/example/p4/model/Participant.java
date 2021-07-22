package com.guilhempelissier.mareu.model;

import java.util.UUID;

public class Participant {
	private String id;
	private String emailAddress;

	public Participant(String emailAddress, String id) {
		this.id = id;
		this.emailAddress = emailAddress;
	}

	public Participant(String emailAddress) {
		this.id = UUID.randomUUID().toString();
		this.emailAddress = emailAddress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
