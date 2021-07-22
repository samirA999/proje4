package com.guilhempelissier.mareu.model;

import java.util.UUID;

public class Room {
	private String id;
	private String name;

	public Room(String name, String id) {
		this.id = id;
		this.name = name;
	}

	public Room(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
