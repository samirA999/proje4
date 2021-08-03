package com.guilhempelissier.mareu.service;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.guilhempelissier.mareu.di.DI;
import com.guilhempelissier.mareu.model.Meeting;
import com.guilhempelissier.mareu.model.Participant;
import com.guilhempelissier.mareu.model.Room;
import com.guilhempelissier.mareu.repository.MeetingRepository;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class MeetingRepositoryTest {

	private MeetingRepository repository;

	@Rule
	public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

	@Before
	public void setup() {
		repository = DI.getNewInstanceRepository();
	}

	@Test
	public void getMeetingsWithSuccess() {
		List<Meeting> meetings = repository.getMeetingList().getValue();
		List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETING;
		assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
	}

	@Test
	public void addMeetingWithSuccess() {
		Meeting meetingToAdd = new Meeting(
			new Room("Salle test"),
			"Sujet test",
			1566210000,
			new ArrayList<>(Arrays.asList(
				new Participant("test@example.com"),
				new Participant("test2@example.com"))));
		assertFalse(repository.getMeetingList().getValue().contains(meetingToAdd));
		repository.addMeeting(meetingToAdd);
		assertTrue(repository.getMeetingList().getValue().contains(meetingToAdd));
	}

	@Test
	public void deleteMeetingWithSuccess() {
		Meeting meetingToDelete = repository.getMeetingList().getValue().get(0);
		repository.removeMeeting(meetingToDelete);
		assertFalse(repository.getMeetingList().getValue().contains(meetingToDelete));
	}

	@Test
	public void deleteMeetingByIdWithSuccess() {
		Meeting meetingToDelete = repository.getMeetingList().getValue().get(0);
		repository.deleteMeetingById(meetingToDelete.getId());
		assertFalse(repository.getMeetingList().getValue().contains(meetingToDelete));
	}

	@Test
	public void findMeetingByIdWithSuccess() {
		Meeting meetingToFind = repository.getMeetingList().getValue().get(0);
		List<Meeting> foundMeetings = repository.filterMeetingsById(meetingToFind.getId());
		assertTrue(foundMeetings.contains(meetingToFind));
		assertEquals(1, foundMeetings.size());
	}
}

