package com.example.p4.di;


public class DI {

	private static MeetingApiService service = new DummyMeetingApiService();
	public static MeetingApiService getMeetingApiService() {
		return service;
	}
	public static MeetingApiService getNewInstanceApiService() {
		return new DummyMeetingApiService();
	}

	private static MeetingRepository repository = new MeetingRepository();
	public static MeetingRepository getMeetingRepository() {
		return repository;
	}
	public static MeetingRepository getNewInstanceRepository() {
		return new MeetingRepository();
	}
}
