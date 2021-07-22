package com.guilhempelissier.mareu.view.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.guilhempelissier.mareu.R;
import com.guilhempelissier.mareu.model.Room;
import com.guilhempelissier.mareu.viewmodel.MeetingListFilter;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.validator.ChipifyingNachoValidator;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FilterDialog extends DialogFragment {
	private FilterDialogListener listener;

	private Button pickStartButton;
	private Button pickStopButton;
	private ImageButton clearStartButton;
	private ImageButton clearStopButton;
	private NachoTextView chipsTextView;

	private DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE);
	private Calendar startCalendar = Calendar.getInstance();
	private Calendar stopCalendar = Calendar.getInstance();

	private List<String> oldPlaces = new ArrayList<>();

	FilterDialog(MeetingListFilter oldFilter) {
		startCalendar.setTimeInMillis(oldFilter.getDateSlotStartTime());
		stopCalendar.setTimeInMillis(oldFilter.getDateSlotEndTime());

		for (Room room : oldFilter.getAllowedPlaces()) {
			oldPlaces.add(room.getName());
		}
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		listener = (FilterDialogListener) context;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_filter_meetings, null);

		pickStartButton = view.findViewById(R.id.filterMeetingPickStartDateButton);
		pickStopButton = view.findViewById(R.id.filterMeetingPickStopDateButton);
		clearStartButton = view.findViewById(R.id.filterMeetingClearStartDateButton);
		clearStopButton = view.findViewById(R.id.filterMeetingClearStopDateButton);
		chipsTextView = view.findViewById(R.id.filterMeetingChipsTextView);
		chipsTextView.setText(oldPlaces);

		setButtonsText();

		chipsTextView.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
		chipsTextView.enableEditChipOnTouch(true, true);
		chipsTextView.setNachoValidator(new ChipifyingNachoValidator());
		setStartButtonsOnClick();
		setStopButtonsOnClick();

		builder.setTitle(R.string.filter_title)
				.setPositiveButton(R.string.filter_positive_button, (dialogInterface, i) -> listener.onDialogPositiveClick(
						startCalendar.getTimeInMillis(),
						stopCalendar.getTimeInMillis(),
						chipsTextView.getChipValues()
				))
				.setNeutralButton(R.string.filter_negative_button, (dialogInterface, i) -> FilterDialog.this.getDialog().cancel())
				.setView(view);

		return builder.create();
	}

	private void setButtonsText() {
		if (startCalendar.getTimeInMillis() == 0) {
			pickStartButton.setText(R.string.filter_meeting_default_date_button);
		} else {
			pickStartButton.setText(
					df.format(new Date(startCalendar.getTimeInMillis()))
			);
		}

		if (stopCalendar.getTimeInMillis() == 0) {
			pickStopButton.setText(R.string.filter_meeting_default_date_button);
		} else {
			pickStopButton.setText(
					df.format(new Date(stopCalendar.getTimeInMillis()))
			);
		}
	}

	private void setStartButtonsOnClick() {
		pickStartButton.setOnClickListener(view1 -> {
			startCalendar.setTime(new Date());
			DatePickerDialog dateDialog = new DatePickerDialog(requireContext(),
					(datePicker, year, month, dayOfMonth) -> {
						startCalendar.set(year, month, dayOfMonth);
						TimePickerDialog  timeDialog = new TimePickerDialog(requireContext(),
								(timePicker, hourOfDay, minute) -> {
									startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
									startCalendar.set(Calendar.MINUTE, minute);
									pickStartButton.setText(
											df.format(new Date(startCalendar.getTimeInMillis()))
									);
								},
								startCalendar.get(Calendar.HOUR_OF_DAY),
								startCalendar.get(Calendar.MINUTE),
								true
						);
						timeDialog.show();
					},
					startCalendar.get(Calendar.YEAR),
					startCalendar.get(Calendar.MONTH),
					startCalendar.get(Calendar.DAY_OF_MONTH)
			);
			dateDialog.show();
		});

		clearStartButton.setOnClickListener(view1 -> {
			startCalendar.setTimeInMillis(0);
			pickStartButton.setText("None");
		});
	}

	private void setStopButtonsOnClick() {
		pickStopButton.setOnClickListener(view1 -> {
			stopCalendar.setTime(new Date());
			DatePickerDialog dateDialog = new DatePickerDialog(requireContext(),
					(datePicker, year, month, dayOfMonth) -> {
						stopCalendar.set(year, month, dayOfMonth);
						TimePickerDialog  timeDialog = new TimePickerDialog(requireContext(),
								(timePicker, hourOfDay, minute) -> {
									stopCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
									stopCalendar.set(Calendar.MINUTE, minute);
									pickStopButton.setText(
											df.format(new Date(stopCalendar.getTimeInMillis()))
									);
								},
								stopCalendar.get(Calendar.HOUR_OF_DAY),
								stopCalendar.get(Calendar.MINUTE),
								true
						);
						timeDialog.show();
					},
					stopCalendar.get(Calendar.YEAR),
					stopCalendar.get(Calendar.MONTH),
					stopCalendar.get(Calendar.DAY_OF_MONTH)
			);
			dateDialog.show();
		});

		clearStopButton.setOnClickListener(view1 -> {
			stopCalendar.setTimeInMillis(0);
			pickStopButton.setText("None");
		});
	}

	public interface FilterDialogListener {
		void onDialogPositiveClick(long startDateFilter, long stopDateFilter, List<String> allowedPlacesFilter);
	}
}
