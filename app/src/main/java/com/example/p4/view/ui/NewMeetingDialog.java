package com.guilhempelissier.mareu.view.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.guilhempelissier.mareu.R;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.validator.ChipifyingNachoValidator;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewMeetingDialog extends DialogFragment {
	private NewMeetingDialogListener listener;

	private EditText topicEditText;
	private EditText placeEditText;
	private Button datePickerButton;
	private NachoTextView chipsTextView;

	private DateFormat df = DateFormat.getDateTimeInstance();
	private Calendar calendar = Calendar.getInstance();

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		listener = (NewMeetingDialogListener) context;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_add_new_meeting, null);

		calendar.setTime(new Date());

		topicEditText = view.findViewById(R.id.newMeetingTopicEditText);
		placeEditText = view.findViewById(R.id.newMeetingPlaceEditText);
		datePickerButton = view.findViewById(R.id.newMeetingDatePickerButton);
		datePickerButton.setText(df.format(new Date(calendar.getTimeInMillis())));
		chipsTextView = view.findViewById(R.id.newMeetingChipsTextView);

		chipsTextView.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
		chipsTextView.addChipTerminator( ' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
		chipsTextView.enableEditChipOnTouch(true, true);
		chipsTextView.setNachoValidator(new ChipifyingNachoValidator());

		setButtonOnClick();

		builder.setTitle(R.string.add_new_meeting)
				.setPositiveButton(R.string.new_meeting_positive, (dialogInterface, i) -> listener.onDialogPositiveClick(
						topicEditText.getText().toString(),
						placeEditText.getText().toString(),
						calendar.getTimeInMillis(),
						chipsTextView.getChipValues()))
				.setNeutralButton(R.string.new_metting_negative, (dialogInterface, i) -> NewMeetingDialog.this.getDialog().cancel())
				.setView(view);

		return builder.create();
	}

	private void setButtonOnClick() {
		datePickerButton.setOnClickListener(view1 -> {
			DatePickerDialog dateDialog = new DatePickerDialog(requireContext(),
					(datePicker, year, month, dayOfMonth) -> {
						calendar.set(year, month, dayOfMonth);
						TimePickerDialog  timeDialog = new TimePickerDialog(requireContext(),
								(timePicker, hourOfDay, minute) -> {
									calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
									calendar.set(Calendar.MINUTE, minute);
									datePickerButton.setText(
											df.format(new Date(calendar.getTimeInMillis()))
									);
								},
								calendar.get(Calendar.HOUR_OF_DAY),
								calendar.get(Calendar.MINUTE),
								true
						);
						timeDialog.show();
					},
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH)
			);
			dateDialog.show();
		});
	}

	public interface NewMeetingDialogListener {
		void onDialogPositiveClick(String topic, String place, long time, List<String> participants);
	}
}
