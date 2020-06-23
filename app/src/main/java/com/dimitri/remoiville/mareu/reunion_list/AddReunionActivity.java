package com.dimitri.remoiville.mareu.reunion_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dimitri.remoiville.mareu.R;
import com.dimitri.remoiville.mareu.di.DI;
import com.dimitri.remoiville.mareu.model.Participant;
import com.dimitri.remoiville.mareu.model.Reunion;
import com.dimitri.remoiville.mareu.model.Room;
import com.dimitri.remoiville.mareu.service.ReunionApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddReunionActivity extends AppCompatActivity {

    private TextInputLayout mNameReunionInput;
    private TextView mRoomTxt;
    private MaterialButton mPickRoomBtn;
    private TextView mParticipantTxt;
    private MaterialButton mPickParticipantsBtn;
    private TextView mDateTxt;
    private MaterialButton mPickDateBtn;
    private TextView mTimeTxt;
    private MaterialButton mPickTimeBtn;
    private MaterialButton mAddButton;

    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;

    private ReunionApiService mApiService;
    private Context mContext = AddReunionActivity.this;

    private String mPickedDate;
    private Room mPickedRoom;
    private List<Participant> mPickedParticipants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);

        mNameReunionInput = findViewById(R.id.name_reunion_layout);
        mRoomTxt = findViewById(R.id.pick_room_txt);
        mPickRoomBtn = findViewById(R.id.pick_room_btn);
        mParticipantTxt = findViewById(R.id.pick_participant_txt);
        mPickParticipantsBtn = findViewById(R.id.pick_participant_btn);
        mDateTxt = findViewById(R.id.pick_date_txt);
        mPickDateBtn = findViewById(R.id.pick_date_btn);
        mTimeTxt = findViewById(R.id.pick_time_txt);
        mPickTimeBtn = findViewById(R.id.pick_time_btn);
        mAddButton = findViewById(R.id.create_btn);
        mApiService = DI.getReunionApiService();
        mAddButton.setEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GenerateListSingleChoiceRoom();
        GenerateListMultipleChoiceParticipant();
        DisplayDatePicker();
        DisplayTimePicker();

        ManagingAddBtn();
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
                builder.setTitle("Sélectionner la salle");
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
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
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
                builder.setTitle("Sélectionner les participants");
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
                        mParticipantTxt.setText(Integer.toString(mParticipantsItems.size()));
                        mParticipantTxt.setVisibility(View.VISIBLE);
                        for (int i = 0; i < mParticipantsItems.size(); i++){
                            mPickedParticipants.add(participantsList.get(mParticipantsItems.get(i)));
                        }
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
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
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                mDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        int realMonth = month + 1;
                        mDateTxt.setText(day + "/" + realMonth + "/" + year);
                        mDateTxt.setVisibility(View.VISIBLE);
                        mPickedDate = Integer.toString(year) + Integer.toString(realMonth) + Integer.toString(day);
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
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                mTimePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                        mTimeTxt.setText(sHour + "h" + sMinute);
                        mTimeTxt.setVisibility(View.VISIBLE);
                    }
                }, hour, minutes, true);
                mTimePickerDialog.show();
            }
        });
    }

    private void ManagingAddBtn() {
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reunion reunion = new Reunion(
                        System.currentTimeMillis(),
                        mNameReunionInput.getEditText().getText().toString(),
                        mPickedDate,
                        mTimeTxt.getText().toString(),
                        mPickedRoom,
                        mPickedParticipants);
                mApiService.createReunion(reunion);
            }
        });
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddReunionActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}