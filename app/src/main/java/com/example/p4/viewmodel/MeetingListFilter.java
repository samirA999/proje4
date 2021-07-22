package com.guilhempelissier.mareu.viewmodel;

import com.guilhempelissier.mareu.model.Room;

import java.util.List;
import java.util.Objects;

public class MeetingListFilter {
	private long dateSlotStartTime;
	private long dateSlotEndTime;
	private List<Room> allowedPlaces;

	public MeetingListFilter(long dateSlotStartTime, long dateSlotEndTime, List<Room> allowedPlaces) {
		this.dateSlotStartTime = dateSlotStartTime;
		this.dateSlotEndTime = dateSlotEndTime;
		this.allowedPlaces = allowedPlaces;
	}

	public long getDateSlotStartTime() {
		return dateSlotStartTime;
	}

	public void setDateSlotStartTime(long dateSlotStartTime) {
		this.dateSlotStartTime = dateSlotStartTime;
	}

	public long getDateSlotEndTime() {
		return dateSlotEndTime;
	}

	public void setDateSlotEndTime(long dateSlotEndTime) {
		this.dateSlotEndTime = dateSlotEndTime;
	}

	public List<Room> getAllowedPlaces() {
		return allowedPlaces;
	}

	public void setAllowedPlaces(List<Room> allowedPlaces) {
		this.allowedPlaces = allowedPlaces;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		MeetingListFilter otherFilter = (MeetingListFilter) obj;
		boolean attributeEquality = Objects.equals(dateSlotStartTime, otherFilter.dateSlotStartTime)
				&& Objects.equals(dateSlotEndTime, otherFilter.dateSlotEndTime)
				&& Objects.equals(allowedPlaces, otherFilter.allowedPlaces);
		return attributeEquality;
	}
}
