package com.guilhempelissier.mareu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.guilhempelissier.mareu.di.DI;
import com.guilhempelissier.mareu.model.Meeting;
import com.guilhempelissier.mareu.model.Participant;
import com.guilhempelissier.mareu.model.Room;
import com.guilhempelissier.mareu.repository.MeetingRepository;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MeetingListViewModel extends ViewModel {
	private MeetingRepository repository;
	private LiveData<List<Meeting>> meetingList;

	private MutableLiveData<MeetingListFilter> meetingListFilter = new MutableLiveData<>();

	private LiveData<List<FormattedMeeting>> formattedMeetingList;

	public MeetingListViewModel() {
		repository = DI.getNewInstanceRepository();
		meetingList = repository.getMeetingList();
		meetingListFilter.setValue(new MeetingListFilter(0,0,new ArrayList<>()));

		formattedMeetingList = initFormattedList();
	}

	public LiveData<List<FormattedMeeting>> getMeetingListObservable() {
		return formattedMeetingList;
	}

	public LiveData<MeetingListFilter> getMeetingListFilter() {
		return meetingListFilter;
	}

	public void addNewMeeting(String topic, String place, long date, List<String> participants) {
		List<Participant> convertedList = new ArrayList<>();
		for (String participant : participants) {
			convertedList.add(new Participant(participant));
		}

		repository.createNewMeeting(
				new Room(place),
				topic,
				date,
				convertedList
		);
	}

	public void deleteMeetingById(String id) {
		repository.deleteMeetingById(id);
	}

	public void setStartHourFilter(long startTime) {
		MeetingListFilter filter = meetingListFilter.getValue();
		filter.setDateSlotStartTime(startTime);
		meetingListFilter.setValue(filter);
	}

	public void setEndHourFilter(long endTime) {
		MeetingListFilter filter = meetingListFilter.getValue();
		filter.setDateSlotEndTime(endTime);
		meetingListFilter.setValue(filter);
	}

	public void setAllowedPlaces(List<String> allowedPlaces) {
		MeetingListFilter filter = meetingListFilter.getValue();
		List<Room> places = new ArrayList<>();
		for (String place : allowedPlaces) {
			places.add(new Room(place));
		}
		filter.setAllowedPlaces(places);
		meetingListFilter.setValue(filter);
	}

	public void resetAllowedPlaces() {
		MeetingListFilter filter = meetingListFilter.getValue();
		List<Room> allowedPlaces = filter.getAllowedPlaces();
		allowedPlaces.clear();
		filter.setAllowedPlaces(allowedPlaces);
		meetingListFilter.setValue(filter);
	}

	private LiveData<List<FormattedMeeting>> initFormattedList() {
		final MediatorLiveData result = new MediatorLiveData<List<FormattedMeeting>>();

		result.addSource(meetingList, o -> {
			List<Meeting> filteredMeetings = filterMeetings(meetingList.getValue(), meetingListFilter.getValue());
			List<FormattedMeeting> formattedMeetings = formatMeetings(filteredMeetings);
			result.setValue(formattedMeetings);
		});

		result.addSource(meetingListFilter, o -> {
			List<Meeting> filteredMeetings = filterMeetings(meetingList.getValue(), meetingListFilter.getValue());
			List<FormattedMeeting> formattedMeetings = formatMeetings(filteredMeetings);
			result.setValue(formattedMeetings);
		});

		return result;
	}

	private List<Meeting> filterMeetings(List<Meeting> meetings, MeetingListFilter meetingListFilter) {
		List<Meeting> filteredMeetings = new ArrayList<>();
		long startTime = meetingListFilter.getDateSlotStartTime();
		long endTime = meetingListFilter.getDateSlotEndTime();
		List<Room> allowedPlaces = meetingListFilter.getAllowedPlaces();

		for (Meeting meeting: meetings) {
			boolean isInsideTimeSlot = true;
			if (startTime != 0 && startTime > meeting.getDate()) {
				isInsideTimeSlot = false;
			}
			if (endTime != 0 && endTime < meeting.getDate()) {
				isInsideTimeSlot = false;
			}
			boolean isPlaceAllowed = true;
			if (allowedPlaces.size() != 0) {
				isPlaceAllowed = false;
				for (Room place : allowedPlaces) {
					if (place.getName().equals(meeting.getPlace().getName())) {
						isPlaceAllowed = true;
					}
				}
			}
			if (isInsideTimeSlot && isPlaceAllowed) {
				filteredMeetings.add(meeting);
			}
		}
		return filteredMeetings;
	}

	private List<FormattedMeeting> formatMeetings(List<Meeting> meetings) {
		List<FormattedMeeting> formattedMeetings = new ArrayList<>();

		for (Meeting meeting: meetings) {
			formattedMeetings.add(formatMeeting(meeting));
		}

		return formattedMeetings;
	}

	private FormattedMeeting formatMeeting(Meeting meeting) {
		DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.FRANCE);
		Date meetingDate = new Date(meeting.getDate());

		String title = (meeting.getTopic() + " - " +
				df.format(meetingDate) + " - " +
				meeting.getPlace().getName());

		String description = "";

		if (!meeting.getParticipants().isEmpty()) {
			for (Participant participant : meeting.getParticipants()) {
				description = description + participant.getEmailAddress() + ", ";
			}
			description = description.substring(0, description.length() - 2);
		}

		return new FormattedMeeting(title, description, meeting.getId());
	}
}
