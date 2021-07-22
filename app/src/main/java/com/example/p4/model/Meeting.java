package com.guilhempelissier.mareu.model;

import java.util.List;
import java.util.UUID;

public class Meeting {
	private String id;
	private Room place;
	private String topic;
	private long date;
	private List<Participant> participants;

	public Meeting(Room place, String topic, long date, List<Participant> participants, String id) {
		this.id = id;
		this.place = place;
		this.topic = topic;
		this.date = date;
		this.participants = participants;
	}

	public Meeting(Room place, String topic, long date, List<Participant> participants) {
		this.id = UUID.randomUUID().toString();
		this.place = place;
		this.topic = topic;
		this.date = date;
		this.participants = participants;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Room getPlace() {
		return place;
	}

	public void setPlace(Room place) {
		this.place = place;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public void addParticipant(Participant participant) {
		this.participants.add(participant);
	}

	public void removeParticipant(Participant participant) {
		this.participants.remove(participant);
	}
}
