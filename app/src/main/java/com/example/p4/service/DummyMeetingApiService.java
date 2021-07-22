package com.guilhempelissier.mareu.service;

import com.guilhempelissier.mareu.model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {
	private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

	@Override
	public List<Meeting> getMeetings() {
		return meetings;
	}

	@Override
	public void removeMeeting(Meeting meeting) {
		meetings.remove(meeting);
	}

	@Override
	public void addMeeting(Meeting meeting) {
		meetings.add(meeting);
	}
}
