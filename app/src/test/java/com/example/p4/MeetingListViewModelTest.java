package com.example.p4.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

import com.example.p4.model.Room;

@RunWith(JUnit4.class)
public class MeetingListViewModelTest {

	private MeetingListViewModel vm;

	@Rule
	public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

	@Before
	public void setup() {
		vm = new MeetingListViewModel();
	}

	@Test
	public void getMeetingListFilterWithSuccess() {
		MeetingListFilter expectedFilter = new MeetingListFilter(0,0,new ArrayList<>());
		MeetingListFilter actualFilter = vm.getMeetingListFilter().getValue();
		assertEquals(actualFilter, expectedFilter);
	}

	@Test
	public void setStartHourFilterWithSuccess() {
		long startHour = 1000000;
		vm.setStartHourFilter(startHour);
		MeetingListFilter filter = vm.getMeetingListFilter().getValue();
		assertEquals(startHour, filter.getDateSlotStartTime());
	}

	@Test
	public void setEndHourFilterWithSuccess() {
		long stopHour = 1000000;
		vm.setEndHourFilter(stopHour);
		MeetingListFilter filter = vm.getMeetingListFilter().getValue();
		assertEquals(stopHour, filter.getDateSlotEndTime());
	}

	@Test
	public void setAllowedPlacesWithSuccess() {
		List<String> allowedPlacesStrings = Arrays.asList(
				"Salle 1"
		);
		List<Room> expectedPlaces = Arrays.asList(
				new Room("Salle 1")
		);
		vm.setAllowedPlaces(allowedPlacesStrings);
		MeetingListFilter filter = vm.getMeetingListFilter().getValue();
		assertEquals(expectedPlaces.get(0).getName(), filter.getAllowedPlaces().get(0).getName());
	}

	@Test
	public void resetAllowedPlacesWithSuccess() {
		List<String> allowedPlacesStrings = Arrays.asList(
				"Salle 1"
		);
		vm.setAllowedPlaces(allowedPlacesStrings);
		MeetingListFilter filter = vm.getMeetingListFilter().getValue();
		assertEquals(1, filter.getAllowedPlaces().size());
		vm.resetAllowedPlaces();
		filter = vm.getMeetingListFilter().getValue();
		assertEquals(0, filter.getAllowedPlaces().size());
	}

	@Test
	public void filterMeetingsWithSuccess() {
		vm.setAllowedPlaces(Arrays.asList("Salle 2"));
		LiveData<List<FormattedMeeting>> filteredMeetings = vm.getMeetingListObservable();
		filteredMeetings.observeForever(formattedMeetings -> {});
		assertEquals(filteredMeetings.getValue().size(), 1);
	}
}
