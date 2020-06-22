package com.dimitri.remoiville.mareu.reunion_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dimitri.remoiville.mareu.R;
import com.dimitri.remoiville.mareu.di.DI;
import com.dimitri.remoiville.mareu.model.Participant;
import com.dimitri.remoiville.mareu.model.Room;
import com.dimitri.remoiville.mareu.service.ReunionApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class AddReunionActivity extends AppCompatActivity {

    private TimePicker mTimePicker;
    private TextInputLayout mNameReunionInput;
    private Spinner mRoomSpinner;
    private TextView mParticipantsTxt;
    private MaterialButton mParticipantsButton;
    private MaterialButton mAddButton;

    private ReunionApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);

        mTimePicker = findViewById(R.id.time_in);
        mNameReunionInput = findViewById(R.id.name_reunion_layout);
        mRoomSpinner = findViewById(R.id.room_spinner);
        mParticipantsTxt = findViewById(R.id.pick_participant_txt);
        mParticipantsButton = findViewById(R.id.pick_participant_btn);
        mAddButton = findViewById(R.id.create_btn);
        mApiService = DI.getReunionApiService();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addItemsOnSpinner();
        managingMultipleChoiceParticipant();
    }

    private void addItemsOnSpinner() {
        List<Room> roomsList = mApiService.getRooms();
        List<String> spinnerList = new ArrayList<String>();

        for (Room r: roomsList) {
            spinnerList.add(r.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, spinnerList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRoomSpinner.setAdapter(dataAdapter);
    }

    private void managingMultipleChoiceParticipant(){
        List<Participant> participantsList = mApiService.getParticipants();
        final String[] listItems = new String[participantsList.size()];
        final boolean[] checkedItems = new boolean[participantsList.size()];
        final List<Integer> mUserItems = new ArrayList<>();

        for (int i = 0; i < participantsList.size(); i++) {
            listItems[i] = participantsList.get(i).getName();
        }

        mParticipantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderDialog = new AlertDialog.Builder(AddReunionActivity.this);
                builderDialog.setTitle("Sélectionner les participants");
                builderDialog.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked) {
                            if(! mUserItems.contains(position)){
                                mUserItems.add(position);
                            }
                        } else {
                            if (mUserItems.contains(position)){
                                mUserItems.remove(position);
                            }
                        }
                    }
                });
                builderDialog.setCancelable(false);
                builderDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mParticipantsTxt.setText(Integer.toString(mUserItems.size()));
                    }
                });
                builderDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog = builderDialog.create();
                mDialog.show();
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