package com.example.p4.repository;


import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

//Dans une vraie app, cette classe fait l'arbitrage entre les données persistées en local et les
//call API quand les données locales ne sont pas fraiches.

public class MeetingRepository {
	private MeetingApiService meetingApiService = DI.getNewInstanceApiService();
	public LiveData<List<Meeting>> meetingList;

	public MeetingRepository() {
		meetingList = new MutableLiveData<>();
		((MutableLiveData<List<Meeting>>) meetingList).setValue(meetingApiService.getMeetings());
	}

	public LiveData<List<Meeting>> getMeetingList() {
		return meetingList;
	}

	public void addMeeting(Meeting meeting) {

		meetingApiService.addMeeting(meeting);
		((MutableLiveData<List<Meeting>>) meetingList).setValue(meetingApiService.getMeetings());
	}

	public void createNewMeeting(Room place, String topic, long date, List<Participant> participants) {
		addMeeting(new Meeting(place, topic, date, participants));
	}

	public void removeMeeting(Meeting meeting) {
		meetingApiService.removeMeeting(meeting);
		((MutableLiveData<List<Meeting>>) meetingList).setValue(meetingApiService.getMeetings());
	}

	public void deleteMeetingById(String id) {
		List<Meeting> meetingsToRemove = filterMeetingsById(id);

		if (meetingsToRemove.size() != 0) {
			Meeting meetingToRemove = meetingsToRemove.get(0);
			meetingApiService.removeMeeting(meetingToRemove);
			((MutableLiveData<List<Meeting>>) meetingList).setValue(meetingApiService.getMeetings());
		}
	}

	@VisibleForTesting
	public List<Meeting> filterMeetingsById(String id) {
		List<Meeting> result = new ArrayList<>();
		List<Meeting> meetings = meetingApiService.getMeetings();

		for (Meeting meeting : meetings) {
			if (meeting.getId().equals(id)) {
				result.add(meeting);
			}
		}

		return result;
	}
}
