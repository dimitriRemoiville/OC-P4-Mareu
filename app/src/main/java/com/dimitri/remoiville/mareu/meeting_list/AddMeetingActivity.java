package com.dimitri.remoiville.mareu.meeting_list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.dimitri.remoiville.mareu.R;
import com.dimitri.remoiville.mareu.di.DI;
import com.dimitri.remoiville.mareu.model.Meeting;
import com.dimitri.remoiville.mareu.model.Participant;
import com.dimitri.remoiville.mareu.model.Room;
import com.dimitri.remoiville.mareu.service.MeetingApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddMeetingActivity extends AppCompatActivity {

    private TextInputLayout mNameMeetingInput;
    private TextView mRoomTxt;
    private MaterialButton mPickRoomBtn;
    private TextView mParticipantTxt;
    private ImageView mPersonImg;
    private MaterialButton mPickParticipantsBtn;
    private TextView mDateTxt;
    private MaterialButton mPickDateBtn;
    private TextView mTimeTxt;
    private MaterialButton mPickTimeBtn;
    private MaterialButton mAddButton;

    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;

    private MeetingApiService mApiService;
    private final List<Meeting> mMeetings = DI.getMeetingApiService().getMeetings();
    private final Context mContext = AddMeetingActivity.this;

    private String mPickedDate;
    private Room mPickedRoom;
    private final List<Participant> mPickedParticipants = new ArrayList<>();
    private boolean mNameOk;
    private boolean mRoomOk;
    private boolean mParticipantsOk;
    private boolean mDateOk;
    private boolean mTimeOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        mNameMeetingInput = findViewById(R.id.name_meeting_layout);
        mRoomTxt = findViewById(R.id.pick_room_txt);
        mPickRoomBtn = findViewById(R.id.pick_room_btn);
        mParticipantTxt = findViewById(R.id.pick_participant_txt);
        mPersonImg = findViewById(R.id.pick_participant_img);
        mPickParticipantsBtn = findViewById(R.id.pick_participant_btn);
        mDateTxt = findViewById(R.id.pick_date_txt);
        mPickDateBtn = findViewById(R.id.pick_date_btn);
        mTimeTxt = findViewById(R.id.pick_time_txt);
        mPickTimeBtn = findViewById(R.id.pick_time_btn);
        mAddButton = findViewById(R.id.create_btn);
        mApiService = DI.getMeetingApiService();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAddButton.setEnabled(false);
        mNameOk = mRoomOk = mParticipantsOk = mTimeOk = mDateOk = false;

        ManagingNameInputLayout();
        GenerateListSingleChoiceRoom();
        GenerateListMultipleChoiceParticipant();
        DisplayDatePicker();
        DisplayTimePicker();

        ManagingAddBtn();
    }

    private void ManagingNameInputLayout() {
        mNameMeetingInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mNameOk = (editable.length() > 0);
                EnabledAddButton();
            }
        });
    }

    private void GenerateListSingleChoiceRoom() {
        final List<Room> roomsList = mApiService.getRooms();
        final String[] listItems = new String[roomsList.size()];
        final int[] checkedItem = {0};
        final int[] mRoomPosition = new int[1];

        for (int i = 0; i < roomsList.size(); i++) {
            listItems[i] = roomsList.get(i).getName();
        }

        mPickRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(R.string.txt_room_button);
                builder.setSingleChoiceItems(listItems, checkedItem[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        mRoomPosition[0] = position;
                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mRoomTxt.setText(listItems[mRoomPosition[0]]);
                        mRoomTxt.setVisibility(View.VISIBLE);
                        checkedItem[0] = mRoomPosition[0];
                        mPickedRoom = roomsList.get(mRoomPosition[0]);
                        mRoomOk = true;
                        CheckData();
                        EnabledAddButton();
                    }
                });
                builder.setNegativeButton(R.string.txt_cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });
    }

    private void GenerateListMultipleChoiceParticipant(){
        final List<Participant> participantsList = mApiService.getParticipants();
        final String[] listItems = new String[participantsList.size()];
        final boolean[] checkedItems = new boolean[participantsList.size()];
        final List<Integer> mParticipantsItems = new ArrayList<>();

        for (int i = 0; i < participantsList.size(); i++) {
            listItems[i] = participantsList.get(i).getName();
        }

        mPickParticipantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(R.string.txt_participants_button);
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked) {
                            if(! mParticipantsItems.contains(position)){
                                mParticipantsItems.add(position);
                            }
                        } else {
                            if (mParticipantsItems.contains(position)){
                                mParticipantsItems.remove(position);
                            }
                        }
                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mParticipantTxt.setText(mParticipantsItems.size() + " X ");
                        mParticipantTxt.setVisibility(View.VISIBLE);
                        mPersonImg.setVisibility(View.VISIBLE);
                        for (int i = 0; i < mParticipantsItems.size(); i++) {
                            mPickedParticipants.add(participantsList.get(mParticipantsItems.get(i)));
                        }
                        mParticipantsOk = true;
                        EnabledAddButton();

                    }
                });
                builder.setNegativeButton(R.string.txt_cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });
    }

    private void DisplayDatePicker() {
        mPickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                mDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        int realMonth = month + 1;
                        mDateTxt.setText(String.format("%02d", day) + "/" + String.format("%02d", realMonth) + "/" + year);
                        mDateTxt.setVisibility(View.VISIBLE);
                        mPickedDate = year
                                + String.format("%02d", realMonth)
                                + String.format("%02d", day);
                        mDateOk = true;
                        CheckData();
                        EnabledAddButton();
                    }
                }, year, month, day);
                mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePickerDialog.show();
            }
        });
    }

    private void DisplayTimePicker() {
        mPickTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                mTimePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                        mTimeTxt.setText(String.format("%02d", sHour) + "h" + String.format("%02d", sMinute));
                        mTimeTxt.setVisibility(View.VISIBLE);
                        mTimeOk = true;
                        CheckData();
                        EnabledAddButton();
                    }
                }, hour, minutes, true);
                mTimePickerDialog.show();
            }
        });
    }

    private void CheckData() {
        if (mTimeOk && mDateOk && mRoomOk) {
            for (Meeting r : mMeetings) {
                if ((r.getDate().equals(mPickedDate))
                        && (r.getTime().equals(mTimeTxt.getText().toString()))
                        && (r.getRoom().getName().equals(mPickedRoom.getName()))) {
                    mDateOk = false;
                    mTimeOk = false;
                    mRoomOk = false;
                    mRoomTxt.setText("");
                    mRoomTxt.setVisibility(View.GONE);
                    mTimeTxt.setText("");
                    mTimeTxt.setVisibility(View.GONE);
                    mDateTxt.setText("");
                    mDateTxt.setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(R.string.txt_error_room_title)
                            .setMessage(R.string.txt_error_room_message)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            }
        }
    }

    private void ManagingAddBtn() {
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Meeting meeting = new Meeting(
                        System.currentTimeMillis(),
                        mNameMeetingInput.getEditText().getText().toString(),
                        mPickedDate,
                        mTimeTxt.getText().toString(),
                        mPickedRoom,
                        mPickedParticipants);
                mApiService.createMeeting(meeting);
                finish();
            }
        });
    }

    private void EnabledAddButton() {
        if (mNameOk && mRoomOk && mParticipantsOk && mDateOk && mTimeOk) {
            mAddButton.setEnabled(true);
        }
    }

    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}