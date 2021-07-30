package com.example.p4.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import come.example.p4.viewmodel.FormattedMeeting;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.MeetingViewHolder> {


    //implémenté les méthodes
    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull  ViewGroup parent , int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FragmentMeetingBinding binding = FragmentMeetingBinding.inflate(layoutInflater, parent, false);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MeetingListAdapter.MeetingViewHolder holder , int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MeetingViewHolder extends RecyclerView.ViewHolder {
    }

    private List<com.guilhempelissier.mareu.viewmodel.FormattedMeeting>;
    private OnDeleteClickListener listener;
    public MeetingListAdapter(List<FormattedMeeyting> meetings ) {mMeetings = meetings ; }
    public void setData

}
