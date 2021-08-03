package com.example.p4.service;

import com.guilhempelissier.mareu.model.Meeting;
import com.guilhempelissier.mareu.model.Participant;
import com.guilhempelissier.mareu.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

	public static List<Meeting> DUMMY_MEETING = Arrays.asList(
			new Meeting(new Room("Salle 1"), "Reunion 1", 03082018, Arrays.asList(
					new Participant("maxime@lamzone.com"),
					new Participant("alex@lamzone.com"),
					new Participant("paul@lamzone.com"),
			), "1"),
			new Meeting(new Room("Salle 2"), "Reunion 2", 03082019, Arrays.asList(
					new Participant("paul@lamzone.com"),
					new Participant("viviane@lamzone.com"),
					new Participant("maxime@lamzone.com"),
			), "2"),
			new Meeting(new Room("Salle 3"), "Reunion 3", 03082020, Arrays.asList(
					new Participant("amandine@lamzone.com"),
					new Participant("luc@lamzone.com"),
					new Participant("paul@lamzone.com")
			), "3")
	);

	static List<Meeting> generateMeetings() {
		return new ArrayList<>(DUMMY_MEETING);
	}
}
