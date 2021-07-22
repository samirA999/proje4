package com.example.p4;


import android.os.Bundle;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p4.view.ui.MeetingListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.p4.viewmodel.MeetingListViewModel;
import com.example.p4.view.adapter.MeetingListAdapter;
import com.example.p4.viewmodel.FormttedMeeting;
public class MainActivity<MeetingListViewModel> extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MeetingListViewModel meetingListViewModel;
    private MeetingListAdapter meetingListAdapter;

    private FloatingActionButton newMeetingButton;

    private LiveData<List<FormttedMeeting>> meeting;


    @Override
    protected void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        newMeetingButton = findViewById(R.id.meeting_list_addMettingBtn);

        newMeetingButton.setOnClickListener(view -> showNewMeeetingDialog());

        meetingListViewModel = ViewModelProvider.of(this).get(com.example.p4.view.ui.MeetingListViewModel.class);
        meeting.observe(this, formttedMeetingList -> meetingListAdapter.setData(formttedMeetingList) );

        meetingListAdapter.setOnItemDeleteListener(id -> meetingListViewModel.deleteMeetingById(id));


    }

    public void showNewMeeetingDialog()
        NewMeetingDialog dialog = new NewMeetingDialog();
        dialog.show (getSupportFragmentManager(), "NewMeetingDialog");
    }

    public void showFilterDialog(){
        MeetingListFilter currentFilter = meetingListViewModel.getMeetingListFilter().getValue();
        if (currentFilter != null){
            FilterDialog dialog = new FilterDialog(currentFilter)
        }
}
