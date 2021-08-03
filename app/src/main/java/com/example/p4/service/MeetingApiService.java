package com.example.p4.service;

import com.example.p4.model.Meeting;
import java.util.List;

public interface MeetingApiService {
	List<Meeting> getMeetings();

	void removeMeeting(Meeting meeting);
	void addMeeting(Meeting meeting);
}
