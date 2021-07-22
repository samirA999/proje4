package com.guilhempelissier.mareu.service;

import com.guilhempelissier.mareu.model.Meeting;
import com.guilhempelissier.mareu.model.Participant;
import com.guilhempelissier.mareu.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

	public static List<Meeting> DUMMY_MEETING = Arrays.asList(
			new Meeting(new Room("Salle 1"), "Reunion 1", 03082018, Arrays.asList(
					new Participant("jp@lizy.fr"),
					new Participant("sam@lizyfr"),
					new Participant("paul@plizy.fr")
			), "1"),
			new Meeting(new Room("Salle 2"), "Reunion 2", 03082019, Arrays.asList(
					new Participant("luc@lizy.fr"),
					new Participant("noe@lizy.fr"),
					new Participant("viviane@lizy.fr")
			), "2"),
			new Meeting(new Room("Salle 3"), "Reunion 3", 03082020, Arrays.asList(
					new Participant("maxime@lizy.fr"),
					new Participant("amandine@lizy.fr"),
					new Participant("paul@lizy.fr")
			), "3")
	);

	static List<Meeting> generateMeetings() {
		return new ArrayList<>(DUMMY_MEETING);
	}
}
