package com.guilhempelissier.mareu.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.guilhempelissier.mareu.databinding.FragmentMeetingBinding;
import com.guilhempelissier.mareu.viewmodel.FormattedMeeting;

import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.MeetingViewHolder> {

	private List<FormattedMeeting> mMeetings;
	private OnDeleteClickListener listener;

	public MeetingListAdapter(List<FormattedMeeting> meetings) {
		mMeetings = meetings;
	}

	public void setData(List<FormattedMeeting> newData) {
		this.mMeetings = newData;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		FragmentMeetingBinding binding = FragmentMeetingBinding.inflate(layoutInflater, parent, false);
		return new MeetingViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
		FormattedMeeting meeting = mMeetings.get(position);
		holder.bind(meeting);
	}

	@Override
	public int getItemCount() {
		return mMeetings.size();
	}

	public class MeetingViewHolder extends RecyclerView.ViewHolder {
		private final FragmentMeetingBinding binding;

		public MeetingViewHolder(FragmentMeetingBinding binding) {
			super(binding.getRoot());
			this.binding = binding;

			binding.meetingDeleteBtn.setOnClickListener(view -> {
				int position = getAdapterPosition();

				if (listener != null && position != RecyclerView.NO_POSITION) {
					listener.onDelete(mMeetings.get(position).getId());
				}
			});

			binding.ContraintLayout.setOnClickListener(view -> {
				if (binding.meetingTitleTextview.getMaxLines() == 1) {
					binding.meetingTitleTextview.setMaxLines(15);
				} else {
					binding.meetingTitleTextview.setMaxLines(1);
				}

				if (binding.meetingDescriptionTextview.getMaxLines() == 1) {
					binding.meetingDescriptionTextview.setMaxLines(15);
				} else {
					binding.meetingDescriptionTextview.setMaxLines(1);
				}
			});
		}

		public void bind(FormattedMeeting meeting) {
			binding.setDescription(meeting.getDescription());
			binding.setTitle(meeting.getTitle());
			binding.executePendingBindings();
		}
	}

	public interface OnDeleteClickListener {
		void onDelete(String id);
	}

	public void setOnItemDeleteListener(OnDeleteClickListener listener) {
		this.listener = listener;
	}
}
