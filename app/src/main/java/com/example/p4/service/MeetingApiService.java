package com.example.p4.service;

import com.guilhempelissier.mareu.model.Meeting;
import java.util.List;

public interface MeetingApiService {
	List<Meeting> getMeetings();

	void removeMeeting(Meeting meeting);
	void addMeeting(Meeting meeting);
}
